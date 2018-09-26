package cn.choleece.bing.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author choleece
 * @date 2018/9/17
 */
@Repository
@Mapper
public interface BaseMapper<T> {

    /**
     * 通过主键获取记录
     * @param pkId
     * @return
     */
    T getOneByPkId(String pkId);
}
