package cn.choleece.bing.admin.controller;

import cn.choleece.bing.common.util.HttpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author choleece
 * @date 2018/9/16
 */
@RestController
public class TestHello {

    private static final Logger logger = LogManager.getLogger("optInfo");

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        logger.info("ddd");
        System.out.println("hello" + HttpUtil.getHttpHeaderValue(request, "header"));
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("ddd", "ddd");
        subject.login(token);
        return "hello";
    }

    @GetMapping("/401")
    public String response401() {
        return "401";
    }
}
