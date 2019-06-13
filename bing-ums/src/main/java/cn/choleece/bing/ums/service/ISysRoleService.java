package cn.choleece.bing.ums.service;

import cn.choleece.bing.ums.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * role service
 * @author choleece
 * @date 2018/11/3
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 更新角色的资源
     * @param roleId
     * @param resourceIds
     */
    void updateRoleRelResource(String roleId, String[] resourceIds);

    /**
     * 判断角色是否关联资源
     * @param roleId
     * @return
     */
    boolean hasRoleRelResource(String roleId);

    /**
     * 判断角色是否关联用户
     * @param roleId
     * @return
     */
    boolean hasRoleRelUser(String roleId);
}
