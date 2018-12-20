package cn.choleece.bing.common.mapper;

import cn.choleece.bing.common.entity.SysRole;
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
public interface SysRoleMapper {

    /**
     * 获取系统的角色
     * @return
     */
    List<SysRole> listSysRole();
}
