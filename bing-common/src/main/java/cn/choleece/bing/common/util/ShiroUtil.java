package cn.choleece.bing.common.util;

import cn.choleece.bing.common.entity.BaseEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * shiro util
 * @author choleece
 * @date 2018/9/17
 */
public class ShiroUtil<T extends BaseEntity> {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    public static <T> T getUserEntity() {
        return (T) getSubject().getPrincipal();
    }

    public static long getUil() {
        return ((BaseEntity) getSubject()).getUid();
    }

    public static String getToken() {
        return ((BaseEntity) getSubject()).getToken();
    }

}
