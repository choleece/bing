package cn.choleece.bing.admin.es.entity;

public class CodeOrder {

    private String id;

    private String orderId;

    private String code;

    private String desc;

    private Integer status;

    private String createTime;

    private Long optTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getOptTime() {
        return optTime;
    }

    public void setOptTime(Long optTime) {
        this.optTime = optTime;
    }

    @Override
    public String toString() {
        return "CodeOrder{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", optTime=" + optTime +
                '}';
    }
}
