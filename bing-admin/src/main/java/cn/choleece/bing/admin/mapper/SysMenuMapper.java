package cn.choleece.bing.admin.mapper;

import cn.choleece.bing.admin.entity.SysMenu;
import cn.choleece.bing.common.util.Query;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统菜单相关mapper
 * @author choleece
 * @date 2018/10/21
 */
@Repository
@Mapper
public interface SysMenuMapper {

    /**
     * list login user's authorized menu
     * @param uid
     * @return
     */
    List<SysMenu> listUserMenu(String uid);
}
