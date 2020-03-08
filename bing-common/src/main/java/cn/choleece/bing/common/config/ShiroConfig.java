package cn.choleece.bing.common.config;

import cn.choleece.bing.common.jwt.JwtFilter;
import cn.choleece.bing.common.shiro.BingRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro 配置
 * @author choleece
 * @date 2018/9/15
 */
@Configuration
public class ShiroConfig {

    @Bean
    EhCacheManager getEhCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    /**
     * 这里暂时现不注册到spring context里，在每一个应用里单独继承BingRealm实现，然后注册进spring context
     * 如果要使用的话，则在方法上加上@Bean("realm")
     * @return
     */
    public BingRealm getRealm() {
        return new BingRealm();
    }

    /**
     * 配置securityManager
     * @param realm 这里的realm是各项目根据自己的情况自己实现继承BingRealm实现，但是bean的名称都得是realm，有各项目自己注册
     * @return
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(BingRealm realm) {

        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        manager.setCacheManager(getEhCacheManager());

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
        subjectDAO.setSessionStorageEvaluator(evaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {

        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        Map<String, Filter> filterMap = new HashMap(1);
        filterMap.put("jwt", new JwtFilter());

        // 设置过滤器
        factoryBean.setFilters(filterMap);

        factoryBean.setSecurityManager(securityManager);

        // 设置未授权的url
        factoryBean.setUnauthorizedUrl("/index/401");

        // 自定义拦截规则
        Map<String, String> shiroFilterMap = new HashMap<>(2);
        shiroFilterMap.put("/index/*", "anon");
//        shiroFilterMap.put("/**", "jwt");

        factoryBean.setFilterChainDefinitionMap(shiroFilterMap);
        return factoryBean;
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
