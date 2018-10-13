package cn.choleece.bing.common.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 常用工具类配置
 * @author choleece
 * @date 2018/9/30
 */
@Configuration
public class CommonConfig {

    @Bean("captchaProducer")
    public DefaultKaptcha getCaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();

        // 验证码配置
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.char.string", "123456789");
        defaultKaptcha.setConfig(new Config(properties));
        return defaultKaptcha;
    }
}
