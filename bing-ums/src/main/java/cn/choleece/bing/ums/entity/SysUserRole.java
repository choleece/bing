package cn.choleece.bing.ums.entity;

import cn.choleece.bing.common.base.BaseEntity;

import java.io.Serializable;

/**
 * 
 * @author choleece
 */
public class SysUserRole extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6910436751463951482L;

	/** 
	 * 用户id.
	 */
	private String userId;

	/** 
	 * 角色id.
	 */
	private String roleId;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "SysUserRole{" +
				"id='" + super.getId() + '\'' +
				", userId='" + userId + '\'' +
				", roleId='" + roleId + '\'' +
				'}';
	}
}
