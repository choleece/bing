package cn.choleece.bing.ums.service.impl;

import cn.choleece.bing.ums.entity.SysUserRole;
import cn.choleece.bing.ums.mapper.SysUserRoleMapper;
import cn.choleece.bing.ums.service.ISysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author choleece
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUserRoles(String userId, List<SysUserRole> userRoles) {
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id", userId));
        saveBatch(userRoles);
    }
}
