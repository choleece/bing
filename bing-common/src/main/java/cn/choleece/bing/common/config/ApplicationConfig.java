package cn.choleece.bing.common.config;

import cn.choleece.bing.common.resolver.CurrentUserHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 系统配置 注册参数处理器， spring 2.0废弃了WebMvcConfigurerAdapter， 官方推荐自己implements WebMvcConfig
 * @author choleece
 * @date 2018/10/10
 */
public class ApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserHandlerMethodArgumentResolver());
    }
}
