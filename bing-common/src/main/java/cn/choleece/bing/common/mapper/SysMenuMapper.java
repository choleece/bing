package cn.choleece.bing.common.mapper;

import cn.choleece.bing.common.entity.SysMenu;
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
