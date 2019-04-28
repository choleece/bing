package cn.choleece.bing.ums.entity;

/**
 * 系统角色
 * @author choleece
 * @date 2018/11/2
 */
public class SysRole {

    private String roleId;

    private String roleName;

    private String createTime;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
