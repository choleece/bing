package cn.choleece.bing.ums.entity;

import cn.choleece.bing.common.base.BaseEntity;

/**
 * 系统角色
 * @author choleece
 * @date 2018/11/2
 */
public class SysRole extends BaseEntity {

    private String roleName;

    private String createTime;

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
