package cn.choleece.bing.ums.service;

import cn.choleece.bing.ums.entity.SysRole;

import java.util.List;

/**
 * role service
 * @author choleece
 * @date 2018/11/3
 */
public interface ISysSoleService {

    /**
     * 获取系统的角色
     * @return
     */
    List<SysRole> listRole();
}
