package cn.choleece.bing.admin.config;

import cn.choleece.bing.admin.shiro.AdminRealm;
import cn.choleece.bing.common.shiro.BingRealm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 利用多态，返回AdminRealm
 * @author choleece
 * @date 2018/9/26
 */
@Configuration
public class AdminShiroConfig {

    @Bean("realm")
    public BingRealm getRealm() {
        return new AdminRealm();
    }
}
