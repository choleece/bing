package cn.choleece.bing.common.config;

import cn.choleece.bing.common.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 过滤器配置
 * @author choleece
 * @date 2018/9/17
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/**");
        Map<String, String> initPapameters = new HashMap<>(1);
        initPapameters.put("excludedPages", "/topic/update,/topic/save,/goods/save,/goods/update");
        filterRegistrationBean.setInitParameters(initPapameters);
        return filterRegistrationBean;
    }
}
