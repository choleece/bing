package cn.choleece.bing.ums.controller;

import cn.choleece.bing.common.base.BaseController;
import cn.choleece.bing.ums.service.ISysRoleService;
import cn.choleece.bing.common.util.R;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author choleece
 * @date 2018/11/3
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
    @Autowired
    private ISysRoleService sysSoleService;

    private static final Logger looger = LogManager.getLogger(SysRoleController.class);

    @GetMapping("")
    public String listSysRole() {
        return R.ok(sysSoleService.listRole());
    }
}
