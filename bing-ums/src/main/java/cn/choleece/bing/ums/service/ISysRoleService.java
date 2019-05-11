package cn.choleece.bing.ums.service;

import cn.choleece.bing.ums.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * role service
 * @author choleece
 * @date 2018/11/3
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 获取系统的角色
     * @return
     */
    List<SysRole> listRole();

    /**
     *
     * @return
     */
    List<SysRole> secondListSysRole();
}
