package cn.choleece.bing.ums.entity;

import cn.choleece.bing.common.base.BaseEntity;

/**
 *
 * @author choleece
 * @date 2019/5/27
 */
public class SysDept extends BaseEntity {

    /**
     * 部门父id
     */
    private String parentId;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 序列号
     */
    private Integer orderNum;

    /**
     * 是否删除 0-否 1-是， 默认0
     */
    private Integer delFlag;

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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
