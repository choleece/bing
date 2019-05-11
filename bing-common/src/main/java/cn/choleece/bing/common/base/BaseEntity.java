package cn.choleece.bing.common.base;

import com.baomidou.mybatisplus.annotation.TableId;

/**
 * Created by choleece on 2019/5/12.
 */
public class BaseEntity {

    @TableId
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
