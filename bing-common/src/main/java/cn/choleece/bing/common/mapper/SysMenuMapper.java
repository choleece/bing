package cn.choleece.bing.common.mapper;

import cn.choleece.bing.common.entity.SysMenu;
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

    /**
     * 统计满足条件的menu的总数
     * @param query
     * @return
     */
    int countMenus(Query query);

    /**
     * 获取满足条件的menu列表
     * @param query
     * @return
     */
    List<SysMenu> listMenu(Query query);
}
