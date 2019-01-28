package cn.choleece.bing.admin.controller;

import cn.choleece.bing.admin.service.ISysUserService;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录相关
 * @author choleece
 * @date 2018/10/13
 */
@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private ISysUserService userService;

    private static final Logger logger = LogManager.getLogger(IndexController.class);

    @PostMapping("/login")
    public String login(@RequestBody JSONObject json) throws Exception {
        logger.info("---user login--- " + json.toString());

        String username = json.getString("username");
        String password = json.getString("password");
        return userService.login(username, password);
    }

}
