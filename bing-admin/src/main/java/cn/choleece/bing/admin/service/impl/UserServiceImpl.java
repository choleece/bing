package cn.choleece.bing.admin.service.impl;

import cn.choleece.bing.admin.entity.SysUser;
import cn.choleece.bing.admin.mapper.UserMapper;
import cn.choleece.bing.admin.service.IUserService;
import cn.choleece.bing.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author choleece
 * @date 2018/9/26
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements IUserService {

    @Autowired
    private UserMapper<SysUser> userMapper;

    @Override
    public SysUser getUserByName(String username) throws Exception {
        return userMapper.getUserByUsername(username);
    }
}
