package cn.choleece.bing.admin.controller;

import cn.choleece.bing.common.util.R;
import cn.choleece.bing.ums.service.ISysRoleService;
import cn.choleece.bing.ums.service.ISysUserService;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private ISysRoleService sysRoleService;

    private static final Logger logger = LogManager.getLogger(IndexController.class);

    @PostMapping("/login")
    public String login(@RequestBody JSONObject json) throws Exception {
        logger.info("---user login--- " + json.toString());

        String username = json.getString("username");
        String password = json.getString("password");
        return userService.login(username, password);
    }

    @RequestMapping("/first")
    public String defaultDataSource() {
        return R.ok(sysRoleService.listRole());
    }

    @RequestMapping("/second")
    public String secondDataSource() {
        return R.ok(sysRoleService.secondListSysRole());
    }

}
