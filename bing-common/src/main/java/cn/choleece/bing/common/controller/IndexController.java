package cn.choleece.bing.common.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * 登录公共类
 * @author choleece
 * @date 2018/9/30
 */
@RestController
@RequestMapping("/index")
public abstract class IndexController extends BaseController {
    @Autowired
    private Producer captchaProducer;

    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = captchaProducer.createText();
        // 生成验证码图片
        BufferedImage image = captchaProducer.createImage(text);
        // 保存在session里

    }
}
