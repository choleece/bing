package cn.choleece.bing.ums.controller;

import cn.choleece.bing.common.constant.CommonConstant;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录公共类
 * @author choleece
 * @date 2018/9/30
 */
@RestController
public class CommonController extends BaseController {
    @Autowired
    private Producer captchaProducer;

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = captchaProducer.createText();
        // 生成验证码图片
        BufferedImage image = captchaProducer.createImage(text);
        // 保存在session里
        request.getSession().removeAttribute(CommonConstant.KEY_CAPTCHA);
        request.getSession().setAttribute(CommonConstant.KEY_CAPTCHA, text);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
    }
}
