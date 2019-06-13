package cn.choleece.bing.admin.controller;

import cn.choleece.bing.admin.constant.AdminConstant;
import cn.choleece.bing.common.base.BaseController;
import cn.choleece.bing.common.cache.J2CacheUtil;
import cn.choleece.bing.common.constant.CommonConstant;
import cn.choleece.bing.common.util.CommonUtil;
import cn.choleece.bing.common.util.LoginAesUtil;
import cn.choleece.bing.common.util.R;
import cn.choleece.bing.common.util.ValidateUtil;
import cn.choleece.bing.common.vo.LoginUser;
import cn.choleece.bing.ums.entity.SysUser;
import cn.choleece.bing.ums.service.ISysUserService;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 * @author choleece
 * @date 2018/10/13
 */
@RestController
@RequestMapping("/index")
@Api(description = "用户登录、退出、验证码等接口")
public class IndexController extends BaseController {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private J2CacheUtil j2CacheUtil;
    @Autowired
    private Producer captchaProducer;

    private static final Logger logger = LogManager.getLogger(IndexController.class);
    private static Logger sfUserManagerLog = LogManager.getLogger("cn.sf.UserManager.Log");

    @ApiOperation(value = "获取验证码", httpMethod = "GET")
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = captchaProducer.createText();
        // 生成验证码图片
        BufferedImage image = captchaProducer.createImage(text);
        // 保存在session里
        request.getSession().removeAttribute(CommonConstant.KEY_CAPTCHA);
        request.getSession().setAttribute(CommonConstant.KEY_CAPTCHA, text);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
    }

    @PostMapping("/login")
    public String login(@RequestBody JSONObject json, HttpServletRequest request) throws Exception {
        String captcha = json.getString("captcha");
        String originCaptcha = CommonUtil.objToString(request.getSession().getAttribute(CommonConstant.KEY_CAPTCHA));
        request.getSession().removeAttribute(CommonConstant.KEY_CAPTCHA);
        if (StringUtils.isEmpty(originCaptcha) || !originCaptcha.toLowerCase().equals(captcha.toLowerCase())) {
            return R.error("验证码错误");
        }
        String pwd = LoginAesUtil.aesDecrypt(json.getString("userName"), json.getString("password"));
        return userService.login(json.getString("userName"), pwd, request);
    }

    @PostMapping("/pwd/alter")
    public String alterUserPwd(@RequestBody JSONObject json) throws Exception {
        LoginUser loginUser = getLoginUser();
        String oldPwd = json.getString("oldPwd");
        if (StringUtils.isEmpty(oldPwd)) {
            return R.error("原始密码不能为空");
        }
        String newPwd = json.getString("newPwd");
        if (!ValidateUtil.isValidPassword(newPwd)) {
            return R.error("密码过于简单，请输入6-18位带有数字、小写字母、大写字母、特殊字符任选三种的密码");
        }
        SysUser user = userService.getById(loginUser.getUid());
        if (!oldPwd.equals(user.getPassword())) {
            return R.error("请输入正确的原始密码");
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(loginUser.getUid());
        sysUser.setPassword(newPwd);
        userService.updateById(sysUser);
        return R.ok();
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        LoginUser loginUser = getLoginUser();
        j2CacheUtil.remove(J2CacheUtil.SYS_PERM_CACHE_NAME, AdminConstant.PERMS_LIST + "-" + getUid());
        HttpSession session = request.getSession();
        session.removeAttribute(CommonConstant.CURRENT_USER);
        SecurityUtils.getSubject().logout();
        return R.ok();
    }

    /**
     * 未授权跳转，返回状态码告诉前端重新登录
     * @return
     */
    @RequestMapping("/401")
    public void unAuthorized() {
        throw new UnauthorizedException("登录超时，请重新登录");
    }

    /**
     * 返回没有权限
     */
    @RequestMapping("/403")
    public void noAuthorization() {
        throw new UnauthenticatedException("无访问权限");
    }
}
