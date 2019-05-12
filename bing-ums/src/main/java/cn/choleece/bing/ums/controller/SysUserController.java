package cn.choleece.bing.ums.controller;

import cn.choleece.bing.common.annotation.CurrentUser;
import cn.choleece.bing.common.base.BaseController;
import cn.choleece.bing.common.constant.CommonConstant;
import cn.choleece.bing.common.util.R;
import cn.choleece.bing.common.vo.LoginUser;
import cn.choleece.bing.ums.service.ISysUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author choleece
 * @date 2018/10/13
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {
    @Autowired
    private ISysUserService userService;

    private static final Logger logger = LogManager.getLogger(SysUserController.class);


    @GetMapping("")
    public String listUser(HttpServletRequest request) {

        System.out.println(request.getSession().getId());
        System.out.println(request.getSession().getAttribute("name"));

        System.out.println(SecurityUtils.getSubject().getSession().getId());
        System.out.println(SecurityUtils.getSubject().getSession().getAttribute(CommonConstant.CURRENT_USER));

        return R.ok();
    }

    @GetMapping("/{userId}")
    public String getUserDetail(@CurrentUser LoginUser loginUser, @PathVariable String userId) {
        System.out.println(loginUser.toString());
        return R.ok();
    }

    @GetMapping("/current/info")
    public String getCurrentUserRoleInfo(@CurrentUser LoginUser loginUser) {
        return R.ok(userService.getCurrentUserRoleInfo(loginUser.getUid()));
    }
}
