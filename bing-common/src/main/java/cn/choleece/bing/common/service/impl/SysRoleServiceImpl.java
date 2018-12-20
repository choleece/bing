package cn.choleece.bing.common.service.impl;

import cn.choleece.bing.common.entity.SysRole;
import cn.choleece.bing.common.mapper.SysRoleMapper;
import cn.choleece.bing.common.service.ISysSoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by choleece on 2018/11/3.
 */
@Service
public class SysRoleServiceImpl implements ISysSoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> listRole() {
        return sysRoleMapper.listSysRole();
    }
}
