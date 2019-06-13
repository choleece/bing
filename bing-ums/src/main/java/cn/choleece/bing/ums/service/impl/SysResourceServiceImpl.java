package cn.choleece.bing.ums.service.impl;

import cn.choleece.bing.ums.entity.SysResource;
import cn.choleece.bing.ums.entity.SysRoleResource;
import cn.choleece.bing.ums.mapper.SysResourceMapper;
import cn.choleece.bing.ums.mapper.SysRoleResourceMapper;
import cn.choleece.bing.ums.service.ISysResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {
    @Autowired
    private SysResourceMapper resourceMapper;
    @Autowired
    private SysRoleResourceMapper roleResourceMapper;

    @Override
    public List<Map> listResourceByUserId(String userId) {
        return resourceMapper.listResourceByUserId(userId);
    }

    @Override
    public List<Map> listUserFunByMenu(String userId, String menuId) {
        return resourceMapper.listUserFunByMenu(userId, menuId);
    }

    @Override
    public boolean hasResourceRelRole(String resourceId) {
        return roleResourceMapper.selectCount(new QueryWrapper<SysRoleResource>().eq("resource_id", resourceId)) > 0;
    }
}
