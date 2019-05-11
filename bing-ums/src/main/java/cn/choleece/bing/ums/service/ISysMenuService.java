package cn.choleece.bing.ums.service;

import cn.choleece.bing.ums.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * system menu service
 * @author choleece
 * @date 2018/10/22
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * list login user's authorized menu
     * @param uid
     * @return
     */
    List<SysMenu> listUserMenu(String uid);
}
