package cn.choleece.bing.common.entity;

import java.io.Serializable;

/**
 * 基础登录用户类，
 * @author choleece
 * @date 2018/9/17
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 8422492159378036911L;

    private long uid;

    private String token;

    public BaseEntity() {
    }

    public BaseEntity(long uid) {
        this.uid = uid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
