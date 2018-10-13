package cn.choleece.bing.admin.service;

import cn.choleece.bing.admin.entity.SysUser;
import cn.choleece.bing.common.util.R;

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
    R login(String username, String pwd);
}
