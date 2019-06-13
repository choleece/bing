package cn.choleece.bing.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 放在jwt里的用户信息
 * @author choleece
 * @date 2018/10/17
 */
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 8422492159378036911L;

    private String uid;

    private String userName;

    private String nickName;

    private String roleId;

    private List roleList;

    private String token;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List getRoleList() {
        return roleList;
    }

    public void setRoleList(List roleList) {
        this.roleList = roleList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

