package cn.choleece.bing.common.datasource.dynamic.config;

import cn.choleece.bing.common.constant.DataSourceName;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置
 * @author choleece
 * @date 2019/4/28
 */
@Configuration
public class DynamicDataSourceConfig {

    /**
     * 这里在配多数据源多时候有一个坑，看源码发现，在mybatis-plus默认配置里，会有一个
     * @return
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.default" )
    public DataSource defaultDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.follower" )
    public DataSource followDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        Map< Object, Object > targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceName.DEFAULT, defaultDataSource());
        targetDataSources.put(DataSourceName.FOLLOWER, followDataSource());

        // 添加数据源
        dynamicDataSource.setTargetDataSources(targetDataSources);
        // 设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource());
        return dynamicDataSource;
    }
}
