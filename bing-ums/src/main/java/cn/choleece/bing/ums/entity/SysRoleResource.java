package cn.choleece.bing.ums.entity;

import cn.choleece.bing.common.base.BaseEntity;

import java.io.Serializable;

/**
 * @author choleece
 */
public class SysRoleResource extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2181908711110584097L;

	private String roleId;

	private String resourceId;

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public String toString() {
		return "SysRoleResource{" +
				"id='" + super.getId() + '\'' +
				", roleId='" + roleId + '\'' +
				", resourceId='" + resourceId + '\'' +
				'}';
	}
}
