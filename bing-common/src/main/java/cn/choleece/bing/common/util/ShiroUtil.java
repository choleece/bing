package cn.choleece.bing.common.util;

import cn.choleece.bing.common.entity.CurUser;
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

    public static CurUser getUserEntity() {
        return (CurUser) getSubject().getPrincipal();
    }

    public static String getUid() {
        return ((CurUser) getSubject()).getUid();
    }

    public static String getToken() {
        return ((CurUser) getSubject()).getToken();
    }

    public static void setSessionAttritube(String key, Object value) {
        getSession().setAttribute(key, value);
    }

}
