package cn.choleece.bing.admin.service.impl;

import cn.choleece.bing.admin.constant.ResponseMsg;
import cn.choleece.bing.admin.entity.SysUser;
import cn.choleece.bing.admin.mapper.UserMapper;
import cn.choleece.bing.admin.service.IUserService;
import cn.choleece.bing.common.service.impl.BaseServiceImpl;
import cn.choleece.bing.common.util.R;
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
public class UserServiceImpl extends BaseServiceImpl implements IUserService {

    @Autowired
    private UserMapper<SysUser> userMapper;

    @Override
    public SysUser getUserByName(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public R login(String username, String pwd) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, pwd);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
        return R.ok();
    }
}
