package cn.choleece.bing.common.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录公共类
 * @author choleece
 * @date 2018/9/30
 */
@Api(value="/", tags="冰公共接口")
@RestController
@RequestMapping("/")
public class CommonController extends BaseController {
    @Autowired
    private Producer captchaProducer;

    @ApiOperation(value="获取图片验证码", notes = "获取图片验证码")
    @GetMapping("captcha")
    public void getCaptcha(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = captchaProducer.createText();
        // 生成验证码图片
        BufferedImage image = captchaProducer.createImage(text);
        // 保存在session里

        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
    }
}
