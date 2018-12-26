package cn.choleece.bing.common.util;

import cn.choleece.bing.common.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * shiro util
 * @author choleece
 * @date 2018/9/17
 */
public class ShiroUtil {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    public static LoginUser getUserEntity() {
        return (LoginUser) getSubject().getPrincipal();
    }

    public static String getUid() {
        return getUserEntity().getUid();
    }

    public static String getToken() {
        return getUserEntity().getToken();
    }

    public static void setSessionAttritube(String key, Object value) {
        getSession().setAttribute(key, value);
    }

}
