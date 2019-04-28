package cn.choleece.bing.ums.mapper;

import cn.choleece.bing.ums.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author choleece
 * @date 2018/11/2
 */
@Repository
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole>{

    /**
     * 获取系统的角色
     * @return
     */
    List<SysRole> listSysRole();
}
