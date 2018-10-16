package cn.choleece.bing.admin.shiro;

import cn.choleece.bing.admin.constant.ResponseMsg;
import cn.choleece.bing.admin.entity.SysUser;
import cn.choleece.bing.admin.service.IUserService;
import cn.choleece.bing.common.constant.CommonConstant;
import cn.choleece.bing.common.entity.CurUser;
import cn.choleece.bing.common.shiro.BingRealm;
import cn.choleece.bing.common.util.PwdUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 根据自己的情况实现realm的鉴权方式，需要继承BingRealm
 * @author choleece
 * @date 2018/9/26
 */
public class AdminRealm extends BingRealm {
    @Autowired
    private IUserService userService;

    /**
     * 用户锁定状态
     */
    private static final String INACTIVE_USER_STATUS = "0";

    @Override
    public boolean supports(AuthenticationToken token) {
        return super.supports(token);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return super.doGetAuthorizationInfo(principalCollection);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username = (String) authenticationToken.getPrincipal();
        String pwd = new String((char[]) authenticationToken.getCredentials());

        SysUser user = userService.getUserByName(username);
        if (user == null) {
            throw new UnknownAccountException(ResponseMsg.INCORRECT_PASSWORD);
        }

        if (!PwdUtil.genPwd(pwd).equals(user.getPassword())) {
//            throw new IncorrectCredentialsException(ResponseMsg.INCORRECT_PASSWORD);
        }

        if (INACTIVE_USER_STATUS.equals(user.getStatus())) {
            throw new LockedAccountException(ResponseMsg.LOCKED_ACCOUNT);
        }

        /**
         * 把登录用户信息放到shiro session 里，也可以放在缓存里，也可以在此生成token，将token返回给前端
         */
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);

        // 将登录用户信息放到session里，current user 可以根据需要，存放不同的信息
        session.setAttribute(CommonConstant.CURRENT_USER, new CurUser.CurUserBuilder().uid(user.getUid()).build());

        // 在此位置，也可以将有必要的信息放到ehcache或者redis等缓存里

        return new SimpleAuthenticationInfo(user, authenticationToken.getCredentials(), "realm");
    }
}
