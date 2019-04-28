package cn.choleece.bing.ums.service.impl;

import cn.choleece.bing.ums.entity.SysRole;
import cn.choleece.bing.ums.mapper.SysRoleMapper;
import cn.choleece.bing.ums.service.ISysSoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by choleece on 2018/11/3.
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysSoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> listRole() {
        return sysRoleMapper.listSysRole();
    }
}
