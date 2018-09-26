package cn.choleece.bing.admin.service;

import cn.choleece.bing.admin.entity.SysUser;

/**
 *
 * @author choleece
 * @date 2018/9/26
 */
public interface IUserService {

    /**
     * 通过用户名获取用户，在注册或创建用户时，用户名不能重复
     * @param username
     * @return
     */
    SysUser getUserByName(String username) throws Exception;
}
