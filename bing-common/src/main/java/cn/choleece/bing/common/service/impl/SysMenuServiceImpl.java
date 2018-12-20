package cn.choleece.bing.common.service.impl;

import cn.choleece.bing.common.entity.SysMenu;
import cn.choleece.bing.common.mapper.SysMenuMapper;
import cn.choleece.bing.common.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * sys menu service
 * @author choleece
 * @date 2018/10/22
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl implements ISysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> listUserMenu(String uid) {
        return sysMenuMapper.listUserMenu(uid);
    }
}
