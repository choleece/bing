package cn.choleece.bing.admin.shiro;

import cn.choleece.bing.admin.constant.AdminConstant;
import cn.choleece.bing.common.constant.CommonResponseMsg;
import cn.choleece.bing.ums.entity.SysUser;
import cn.choleece.bing.ums.mapper.SysResourceMapper;
import cn.choleece.bing.ums.service.ISysUserService;
import cn.choleece.bing.common.cache.J2CacheUtil;
import cn.choleece.bing.common.constant.CommonConstant;
import cn.choleece.bing.common.shiro.BingRealm;
import cn.choleece.bing.common.vo.LoginUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 根据自己的情况实现realm的鉴权方式，需要继承BingRealm
 * @author choleece
 * @date 2018/9/26
 */
public class AdminRealm extends BingRealm {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private J2CacheUtil j2CacheUtil;
    @Autowired
    private SysResourceMapper resourceMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return super.supports(token);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LoginUser user = (LoginUser) principalCollection.getPrimaryPrincipal();

        List<String> perms = (List<String>) j2CacheUtil.get(J2CacheUtil.SYS_PERM_CACHE_NAME, AdminConstant.PERMS_LIST + "-" + user.getUid());
        Set<String> setPerms = new HashSet<>();
        if (perms != null && perms.size() != 0) {
            for (String perm : perms) {
                if (StringUtils.isEmpty(perm)) {
                    continue;
                }
                setPerms.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(setPerms);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String userName = (String) authenticationToken.getPrincipal();
        String pwd = new String((char[]) authenticationToken.getCredentials());

        SysUser user = userService.getOne(new QueryWrapper<SysUser>().eq("user_name", userName));
        if (!pwd.equals(user.getPassword())) {
            throw new IncorrectCredentialsException(CommonResponseMsg.INCORRECT_PASSWORD);
        }
        /**
         * 把登录用户信息放到shiro session 里，也可以放在缓存里，也可以在此生成token，将token返回给前端
         */
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);

        // 将用户权限放入缓存
        List<String> perms = resourceMapper.listUserPermissions(user.getId());
        j2CacheUtil.put(J2CacheUtil.SYS_PERM_CACHE_NAME, AdminConstant.PERMS_LIST + "-" + user.getId(), perms);

        LoginUser loginUser = new LoginUser();
        loginUser.setUid(user.getId());
        loginUser.setUserName(user.getUserName());
        loginUser.setNickName(user.getNickName());
        loginUser.setRoleList(user.getRoleList());
        // 将登录用户信息放到session里，current user 可以根据需要，存放不同的信息
        session.setAttribute(CommonConstant.CURRENT_USER, loginUser);
        return new SimpleAuthenticationInfo(loginUser, authenticationToken.getCredentials(), "realm");
    }
}
