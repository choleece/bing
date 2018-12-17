package cn.choleece.bing.admin.mapper;

import cn.choleece.bing.admin.entity.SysUser;
import cn.choleece.bing.common.util.Query;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户名获取唯一用户
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);
}
