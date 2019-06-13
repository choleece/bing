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

	/** 
	 * 设置用户与权限关联的用户id.
	 */
	private String authUserId;

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

	public String getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(String authUserId) {
		this.authUserId = authUserId;
	}

	@Override
	public String toString() {
		return "SysUserRole{" +
				"id='" + super.getId() + '\'' +
				", userId='" + userId + '\'' +
				", roleId='" + roleId + '\'' +
				", authUserId='" + authUserId + '\'' +
				'}';
	}
}
