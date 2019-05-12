package cn.choleece.bing.ums.entity;

import cn.choleece.bing.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 系统后台用户
 * @author choleece
 * @date 2018/9/26
 */
public class SysUser extends BaseEntity {

    private String username;

    /**
     * 用户昵称
     */
    private String nickName;

    @TableField(exist = false)
    private String roleId;

    @TableField(exist = false)
    private String roleName;

    private String salt;

    private String password;

    private String mobile;

    private String email;

    private String status;

    private String createTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
