package cn.choleece.bing.common.vo;

import java.io.Serializable;

/**
 * 放在jwt里的用户信息
 * @author choleece
 * @date 2018/10/17
 */
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 8422492159378036911L;

    private String uid;

    private String username;

    private String roleId;

    private String token;

    public LoginUser() {
    }

    private LoginUser(LoginUser.LoginUserBuilder builder) {
        this.uid = builder.uid;
        this.token = builder.token;
        this.username = builder.username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public static class LoginUserBuilder {

        private String uid;

        private String token;

        private String username;

        public LoginUserBuilder uid(String uid) {
            this.uid = uid;
            return this;
        }

        public LoginUserBuilder token(String token) {
            this.token = token;
            return this;
        }

        public LoginUserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public LoginUser build() {
            return new LoginUser(this);
        }
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", roleId='" + roleId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

