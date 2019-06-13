package cn.choleece.bing.common.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 公共的entity
 * @author choleece
 * @date 2019/5/12
 */
public class BaseEntity {

    @TableId(type = IdType.ID_WORKER)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
