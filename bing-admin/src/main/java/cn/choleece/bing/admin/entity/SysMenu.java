package cn.choleece.bing.admin.entity;

/**
 * 系统菜单
 * @author choleece
 * @date 2018/10/21
 */
public class SysMenu {

    /**
     * 菜单id
     */
    private String menuId;

    /**
     * 父级id, 一级菜单的父级菜单为0
     */
    private String parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单点击地址
     */
    private String url;

    /**
     * 渲染菜单所需权限
     */
    private String perms;

    /**
     * 菜单类型 0:目录 1:菜单 2:按钮
     */
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单展示的顺序
     */
    private Integer orderNum;

    /**
     * 菜单状态
     */
    private Integer status;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
