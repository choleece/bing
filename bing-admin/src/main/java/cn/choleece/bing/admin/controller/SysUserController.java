package cn.choleece.bing.admin.controller;

import cn.choleece.bing.admin.service.ISysMenuService;
import cn.choleece.bing.common.constant.CommonConstant;
import cn.choleece.bing.common.controller.BaseController;
import cn.choleece.bing.common.util.HttpUtil;
import cn.choleece.bing.common.util.LogUtil;
import cn.choleece.bing.common.util.R;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    private ISysMenuService sysMenuService;

    private static final Logger logger = LogManager.getLogger(SysUserController.class);

    @GetMapping("/menu")
    public String listUserMenu(HttpServletRequest request) {
        String uid = HttpUtil.getUid(request);
        LogUtil.info(logger, "get user side menus", " uid: " + uid);

        return R.ok(sysMenuService.listUserMenu(uid));
    }

    @GetMapping("/")
    public String listUser(HttpServletRequest request) {

        System.out.println(request.getSession().getId());
        System.out.println(request.getSession().getAttribute("name"));

        System.out.println(SecurityUtils.getSubject().getSession().getId());
        System.out.println(SecurityUtils.getSubject().getSession().getAttribute(CommonConstant.CURRENT_USER));

        return R.ok();
    }
}
