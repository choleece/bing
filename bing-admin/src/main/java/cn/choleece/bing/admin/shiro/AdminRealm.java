package cn.choleece.bing.admin.shiro;

import cn.choleece.bing.common.shiro.BingRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 根据自己的情况实现realm的鉴权方式，需要继承BingRealm
 * @author choleece
 * @date 2018/9/26
 */
public class AdminRealm extends BingRealm {


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
        System.out.println(authenticationToken.getPrincipal() + "hhhhhh");
        return super.doGetAuthenticationInfo(authenticationToken);
    }
}
