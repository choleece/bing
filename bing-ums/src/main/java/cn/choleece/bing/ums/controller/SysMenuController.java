package cn.choleece.bing.ums.controller;

import cn.choleece.bing.ums.service.ISysMenuService;
import cn.choleece.bing.common.util.HttpUtil;
import cn.choleece.bing.common.util.LogUtil;
import cn.choleece.bing.common.util.R;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 菜单相关controller
 * @author choleece
 * @date 2018/10/21
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {
    @Autowired
    private ISysMenuService sysMenuService;

    private static final Logger logger = LogManager.getLogger(SysMenuController.class);

    @GetMapping("")
    public String listAllSysMenus(HttpServletRequest request) {

        String uid = HttpUtil.getUid(request);

        LogUtil.info(logger, "list system user's all authorized menus", "uid: " + uid);

        return R.ok(sysMenuService.listUserMenu(uid));
    }

    @GetMapping("/user")
    public String listUserMenu() {
        String uid = getUid();
        LogUtil.info(logger, "get user side menus", " uid: " + uid);

        return R.ok(sysMenuService.listUserMenu(uid));
    }

    @GetMapping("/test")
    public String listTest() {
        return R.ok();
    }
}