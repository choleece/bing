package cn.choleece.bing.admin.controller;

import cn.choleece.bing.admin.service.IUserService;
import cn.choleece.bing.common.controller.BaseController;
import cn.choleece.bing.common.util.R;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录相关
 * @author choleece
 * @date 2018/10/13
 */
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {
    @Autowired
    private IUserService userService;

    private static final Logger logger = LogManager.getLogger(IndexController.class);

    @PostMapping("/login")
    public R login(String username, String password) throws Exception {

        logger.info("---user login--- username: " + username + " password: " + password);

        return userService.login(username, password);
    }

}
