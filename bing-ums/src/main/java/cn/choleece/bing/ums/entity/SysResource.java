package cn.choleece.bing.ums.entity;

import cn.choleece.bing.common.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author choleece
 */
public class SysResource extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -3529133869649597012L;

	private String name;

	/** 
	 * 1-系统（目录）,第一级目录表示系统
	 * 2-菜单（url地址）
	 * 3-功能（按钮）.
	 */
	private Integer type;

	private String parentId;
	 
	/** 
	 * 标识，用于程序内部识别，比如类型为系统时，根据标示显示是否有后台管理界面.
	 */
	private String identify;

	/** 
	 * 资源地址.
	 */
	private String url;

	/** 
	 * 图标地址.
	 */
	private String imgUrl;

	/** 
	 * 图标样式,如果图标地址为空时，使用图标样式.
	 */
	private String icon;

	/**
	 * 权限，多个权限用,隔开
	 */
	private String perms;

	private String remark;

	/** 
	 * 删除状态 0-未删除 1-删除.
	 */
	private Integer delStatus;

	/** 
	 * 创建人.
	 */
	private String createUser;

	/** 
	 * 创建时间.
	 */
	private Date createDate;

	/**
	 * 启用状态 1-启用 0-禁用
	 */
	private Integer useStatus;
	
	/**
	 * 序号
	 */
	private Integer snum;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIdentify() {
		return this.identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDelStatus() {
		return this.delStatus;
	}

	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}

	public Integer getSnum() {
		return snum;
	}

	public void setSnum(Integer snum) {
		this.snum = snum;
	}

	@Override
	public String toString() {
		return "SysResource{" +
				"id='" + super.getId() + '\'' +
				", name='" + name + '\'' +
				", type=" + type +
				", parentId='" + parentId + '\'' +
				", identify='" + identify + '\'' +
				", url='" + url + '\'' +
				", imgUrl='" + imgUrl + '\'' +
				", icon='" + icon + '\'' +
				", perms='" + perms + '\'' +
				", remark='" + remark + '\'' +
				", delStatus=" + delStatus +
				", createUser='" + createUser + '\'' +
				", createDate=" + createDate +
				", useStatus=" + useStatus +
				", snum=" + snum +
				'}';
	}
}
