package cn.choleece.bing.common.config;

import cn.choleece.bing.common.util.PropertiesFileUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库相关配置
 * @author choleece
 * @date 2018/9/16
 */
@Configuration
@MapperScan("cn.choleece.*.*.mapper")
public class JdbcConfig {

    private static final String DB_CONFIG_NAME = "db";

    private static final String MAPPER_LOCAL = "classpath*:mapper/*/*.xml";

    @Bean("dataSource")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        PropertiesFileUtil instance = PropertiesFileUtil.getInstance(DB_CONFIG_NAME);

        dataSource.setDriverClassName(instance.get("jdbc.master.driveClassName"));
        dataSource.setUrl(instance.get("jdbc.master.url"));
        dataSource.setUsername(instance.get("jdbc.master.username"));
        dataSource.setPassword(instance.get("jdbc.master.password"));
        dataSource.setInitialSize(instance.getInt("jdbc.master.initialSize"));
        dataSource.setMinIdle(instance.getInt("jdbc.master.minIdle"));
        dataSource.setMaxActive(instance.getInt("jdbc.master.maxActive"));
        dataSource.setMaxWait(instance.getInt("jdbc.master.maxWait"));

        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DruidDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DruidDataSource dataSource) throws Exception {
        return createSqlSessionFactory(dataSource, MAPPER_LOCAL);
    }

    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource, String xmlMapperLocations) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        // mybatis分页

        sessionFactoryBean.setMapperLocations((new PathMatchingResourcePatternResolver()).getResources(xmlMapperLocations));
        return sessionFactoryBean.getObject();
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        // 用户名
        initParameters.put("loginUsername", "admin");
        // 密码
        initParameters.put("loginPassword", "admin");
        // 禁用HTML也没上的Reset All功能
        initParameters.put("resetEnable", "false");
        // 设置白名单，配置为空，则所有都可以访问
        initParameters.put("allow", "");
        // 设置黑名单
        initParameters.put("deny", "");

        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
