package cn.choleece.bing.ums.service.impl;

import cn.choleece.bing.ums.entity.SysMenu;
import cn.choleece.bing.ums.mapper.SysMenuMapper;
import cn.choleece.bing.ums.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * sys menu service
 * @author choleece
 * @date 2018/10/22
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> listUserMenu(String uid) {
        return sysMenuMapper.listUserMenu(uid);
    }
}
