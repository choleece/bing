package cn.choleece.bing.admin.controller;

import cn.choleece.bing.common.constant.CommonConstant;
import cn.choleece.bing.common.controller.BaseController;
import cn.choleece.bing.common.util.R;
import org.apache.shiro.SecurityUtils;
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
@RequestMapping("/user")
public class UserController extends BaseController {

    @GetMapping("/")
    public R listUser(HttpServletRequest request) {

        System.out.println(request.getSession().getId());
        System.out.println(request.getSession().getAttribute("name"));

        System.out.println(SecurityUtils.getSubject().getSession().getId());
        System.out.println(SecurityUtils.getSubject().getSession().getAttribute(CommonConstant.CURRENT_USER));

        return R.ok();
    }
}
