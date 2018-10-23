package cn.choleece.bing.admin.service;

import cn.choleece.bing.admin.entity.SysUser;

/**
 *
 * @author choleece
 * @date 2018/9/26
 */
public interface ISysUserService {

    /**
     * 通过用户名获取用户，在注册或创建用户时，用户名不能重复
     * @param username
     * @return
     * @throws Exception
     */
    SysUser getUserByName(String username);

    /**
     * login
     * @param username
     * @param pwd
     * @return
     * @throws Exception
     */
    String login(String username, String pwd);
}
