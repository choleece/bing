package cn.choleece.bing.admin.service;

import cn.choleece.bing.admin.entity.SysMenu;

import java.util.List;

/**
 * system menu service
 * @author choleece
 * @date 2018/10/22
 */
public interface ISysMenuService {

    /**
     * list login user's authorized menu
     * @param uid
     * @return
     */
    List<SysMenu> listUserMenu(String uid);
}
