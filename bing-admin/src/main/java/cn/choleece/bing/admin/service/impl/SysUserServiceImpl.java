package cn.choleece.bing.admin.service.impl;

import cn.choleece.bing.admin.constant.ResponseMsg;
import cn.choleece.bing.admin.entity.SysUser;
import cn.choleece.bing.admin.mapper.SysUserMapper;
import cn.choleece.bing.admin.service.ISysUserService;
import cn.choleece.bing.common.service.impl.BaseServiceImpl;
import cn.choleece.bing.common.util.JwtUtil;
import cn.choleece.bing.common.util.R;
import cn.choleece.bing.common.vo.LoginUser;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author choleece
 * @date 2018/9/26
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public SysUser getUserByName(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public String login(String username, String pwd) {
        if (StringUtils.isBlank(username)) {
            return R.error(ResponseMsg.INCORRECT_PASSWORD);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, pwd);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error(e.getMessage());
        } catch (LockedAccountException e) {
            return R.error(e.getMessage());
        }
        // 执行token或者其他什么策略
        SysUser sysUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
        LoginUser loginUser = new LoginUser();
        loginUser.setUid(sysUser.getUserId());
        loginUser.setUsername(sysUser.getUsername());
        return R.ok(JwtUtil.sign(loginUser));
    }
}
