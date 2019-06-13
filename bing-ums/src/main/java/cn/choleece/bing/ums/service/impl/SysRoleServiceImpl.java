package cn.choleece.bing.ums.service.impl;

import cn.choleece.bing.common.util.SnowFlakeUtil;
import cn.choleece.bing.ums.entity.SysRole;
import cn.choleece.bing.ums.entity.SysRoleResource;
import cn.choleece.bing.ums.entity.SysUserRole;
import cn.choleece.bing.ums.mapper.SysRoleMapper;
import cn.choleece.bing.ums.mapper.SysRoleResourceMapper;
import cn.choleece.bing.ums.mapper.SysUserRoleMapper;
import cn.choleece.bing.ums.service.ISysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 系统角色
 * @author choleece
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Autowired
    private SysRoleResourceMapper roleResourceMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRoleRelResource(String roleId, String[] resourceIds) {
        roleResourceMapper.delete(new QueryWrapper<SysRoleResource>().eq("role_id", roleId));
        for(String resourceId: resourceIds) {
            if(StringUtils.isEmpty(resourceId)) {
                continue;
            }
            SysRoleResource bean = new SysRoleResource();
            bean.setId(SnowFlakeUtil.getStrId());
            bean.setRoleId(roleId);
            bean.setResourceId(resourceId);
            roleResourceMapper.insert(bean);
        }
    }

    @Override
    public boolean hasRoleRelResource(String roleId) {
        List<SysRoleResource> list = roleResourceMapper.selectList(new QueryWrapper<SysRoleResource>().eq("role_id", roleId));
        if(list != null && list.size()>0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasRoleRelUser(String roleId) {
        List<SysUserRole> list = userRoleMapper.selectList(new QueryWrapper<SysUserRole>().eq("role_id", roleId));
        if(list != null && list.size()>0) {
            return true;
        }
        return false;
    }
}
