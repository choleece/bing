package cn.choleece.bing.ums.entity;

import cn.choleece.bing.common.base.BaseEntity;

import java.io.Serializable;

/**
 * 系统角色
 * @author choleece
 * @date 2018/11/2
 */
public class SysRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2885931207321820191L;

    private String roleName;

    private String remark;

    private String createUser;

    private String createDate;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "id='" + super.getId() + '\'' +
                ", roleName='" + roleName + '\'' +
                ", remark='" + remark + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
