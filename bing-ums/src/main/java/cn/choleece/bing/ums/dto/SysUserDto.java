package cn.choleece.bing.ums.dto;

import cn.choleece.bing.ums.entity.SysRole;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;
import java.util.List;

/**
 * @author choleece
 * @ClassName SysUserDto web端传输对象
 * @Description
 * @Date 2019/5/10
 **/
public class SysUserDto {

    private String id;

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
     * 创建时间.
     */
    private Date createDate;

    /**
     * 0-正常，1-删除.
     */
    private Integer delStatus;


    /**
     * 1-启用 0-禁用
     */
    private String usable;

    /**
     * 角色列表
     */
    @TableField(exist = false)
    private List<SysRole> roleList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
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
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
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
        return "SysUserDto{" +
                "id='" + id + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                ", remark='" + remark + '\'' +
                ", createDate=" + createDate +
                ", delStatus=" + delStatus +
                ", usable='" + usable + '\'' +
                ", roleList=" + roleList +
                '}';
    }
}
