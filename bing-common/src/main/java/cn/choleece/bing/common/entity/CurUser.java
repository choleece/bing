package cn.choleece.bing.common.entity;

import java.io.Serializable;

/**
 * 登录用户类
 * @author choleece
 * @date 2018/9/17
 */
public class CurUser implements Serializable {

    private static final long serialVersionUID = 8422492159378036911L;

    private String uid;

    private String token;

    private CurUser(CurUserBuilder builder) {
        this.uid = builder.uid;
        this.token = builder.token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class CurUserBuilder {

        private String uid;

        private String token;

        public CurUserBuilder uid(String uid) {
            this.uid = uid;
            return this;
        }

        public CurUserBuilder token(String token) {
            this.token = token;
            return this;
        }

        public CurUser build() {
            return new CurUser(this);
        }
    }
}
