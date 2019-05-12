package cn.choleece.bing.ums.service;

import cn.choleece.bing.ums.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 * @author choleece
 * @date 2018/9/26
 */
public interface ISysUserService extends IService<SysUser> {

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

    /**
     * 通过用户ID获取用户&角色信息
     * @param userId
     * @return
     */
    SysUser getCurrentUserRoleInfo(String userId);
}
