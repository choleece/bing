package cn.choleece.bing.ums.service;

import cn.choleece.bing.ums.entity.SysUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author choleece
 * @date 2018/9/26
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * login
     * @param username
     * @param pwd
     * @param request
     * @return
     * @throws Exception
     */
    String login(String username, String pwd, HttpServletRequest request);

    /**
     * 通过用户名获取用户信息
     * @param userName
     * @return
     */
    String getUserInfoByUserName(String userName);

    /**
     * 分页数据传
     * @param page
     * @param keyword
     * @return
     */
    Page<SysUser> pageList(Page page, String keyword);
}
