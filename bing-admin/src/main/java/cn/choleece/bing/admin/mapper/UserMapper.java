package cn.choleece.bing.admin.mapper;

import cn.choleece.bing.admin.entity.SysUser;
import cn.choleece.bing.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author choleece
 * @date 2018/9/26
 */
@Repository
@Mapper
public interface UserMapper<T>  {

    /**
     * 通过用户名获取唯一用户
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);
}
