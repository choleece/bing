package cn.choleece.bing.ums.mapper;

import cn.choleece.bing.ums.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author choleece
 * @date 2018/9/26
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户名获取唯一用户
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);

    /**
     * 通过用户id获取用户&角色信息
     * @param userId
     * @return
     */
    SysUser getUserRoleByUserId(String userId);
}
