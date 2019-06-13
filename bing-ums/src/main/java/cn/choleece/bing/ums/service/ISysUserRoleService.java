package cn.choleece.bing.ums.service;

import cn.choleece.bing.ums.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 保存用户角色关系
     * @param userId
     * @param userRoles
     */
    void saveUserRoles(String userId, List<SysUserRole> userRoles);
}
