package cn.choleece.bing.ums.mapper;

import cn.choleece.bing.ums.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统菜单相关mapper
 * @author choleece
 * @date 2018/10/21
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * list login user's authorized menu
     * @param uid
     * @return
     */
    List<SysMenu> listUserMenu(String uid);
}
