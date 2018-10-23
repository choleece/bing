package cn.choleece.bing.admin.mapper;

import cn.choleece.bing.admin.entity.SysUser;
import cn.choleece.bing.common.util.Query;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author choleece
 * @date 2018/9/26
 */
@Repository
@Mapper
public interface SysUserMapper<T>  {

    /**
     * 通过用户名获取唯一用户
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);

    /**
     * 统计满足条件的用户数量
     * @param query
     * @return
     */
    int countSysUsers(Query query);

    /**
     * 获取满足条件的用户
     * @param query
     * @return
     */
    List<SysUser> listSysUser(Query query);
}
