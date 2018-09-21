package cn.choleece.bing.common.config;

import cn.choleece.bing.common.jwt.JwtFilter;
import cn.choleece.bing.common.shiro.BingRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
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

    @Bean("bingRealm")
    public BingRealm getRealm() {
        return new BingRealm();
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(BingRealm bingRealm) {

        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(bingRealm);
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
        factoryBean.setUnauthorizedUrl("");

        // 自定义拦截规则
        Map<String, String> filterRoleMap = new HashMap<>(2);
        filterRoleMap.put("/**", "jwt");
        filterRoleMap.put("/401", "anon");

        factoryBean.setFilterChainDefinitionMap(filterRoleMap);
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
