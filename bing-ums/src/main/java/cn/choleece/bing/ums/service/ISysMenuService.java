package cn.choleece.bing.ums.service;

import cn.choleece.bing.ums.entity.SysMenu;

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
