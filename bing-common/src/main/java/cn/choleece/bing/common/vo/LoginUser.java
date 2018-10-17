package cn.choleece.bing.common.vo;

/**
 * 放在jwt里的用户信息
 * @author choleece
 * @date 2018/10/17
 */
public class LoginUser {

    private String uid;

    private String username;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
