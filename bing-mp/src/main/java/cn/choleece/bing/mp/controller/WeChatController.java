package cn.choleece.bing.mp.controller;

import cn.hutool.http.HttpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by choleece on 2019/6/12.
 */
@RestController
@RequestMapping("/wechat")
public class WeChatController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code, @RequestParam("state") String state){
        System.out.println(code);
        System.out.println(state);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxb3a7e7350226d16e&secret=d51102384d27b67fd45a5fff9b253ccb&code="+code+"&grant_type=authorization_code";
        String str = HttpUtil.get(url);
        System.out.println(str);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {


        String str = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb3a7e7350226d16e&redirect_uri=http%3A%2F%2F127.0.0.1%3A8080%2Fbing%2Fwechat%2Fauth&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

        System.out.println(URLEncoder.encode("http://127.0.0.1:8080/bing/wechat/auth", "utf-8"));
    }
}
