package cn.choleece.bing.ums.entity;

import cn.choleece.bing.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author choleece
 *
 */
public class SysUser extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2748545622999366819L;

	/** 
	 * 用户名
	 */
	private String userName;

	/**
	 * 昵称
	 */
	private String nickName;

	/** 
	 * 1-男，2-女，9-未知
	 */
	private Integer sex;

	/** 
	 * 手机.
	 */
	private String phone;

	/** 
	 * 密码盐
	 */
	private String salt;

	/** 
	 * 邮箱.
	 */
	private String email;

	/**
	 * 备注
	 */
	private String remark;

	/** 
	 * 创建人.
	 */
	private String createUser;

	/** 
	 * 创建时间.
	 */
	private String createTime;

	/** 
	 * 0-正常，1-删除.
	 */
	private Integer delStatus;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 1-启用 0-禁用
	 */
	private String usable;

	/**
	 * 角色
	 */
	@TableField(exist = false)
	private List<SysRole> roleList;

	@TableField(exist = false)
	private String roleId;

	public String getUserName() {
		return this.userName;
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

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getDelStatus() {
		return this.delStatus;
	}

	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

	public String getUsable() {
		return usable;
	}

	public void setUsable(String usable) {
		this.usable = usable;
	}

	public List<SysRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}

	@Override
	public String toString() {
		return "SysUser{" +
				"id='" + super.getId() + '\'' +
				"userName='" + userName + '\'' +
				", nickName='" + nickName + '\'' +
				", sex=" + sex +
				", phone='" + phone + '\'' +
				", salt='" + salt + '\'' +
				", email='" + email + '\'' +
				", remark='" + remark + '\'' +
				", createUser='" + createUser + '\'' +
				", createTime=" + createTime +
				", delStatus=" + delStatus +
				", password='" + password + '\'' +
				", usable='" + usable + '\'' +
				", roleList=" + roleList +
				", roleId='" + roleId + '\'' +
				'}';
	}
}
