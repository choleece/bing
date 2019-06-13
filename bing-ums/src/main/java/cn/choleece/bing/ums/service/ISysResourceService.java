package cn.choleece.bing.ums.service;

import cn.choleece.bing.ums.entity.SysResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface ISysResourceService extends IService<SysResource> {

    List<Map> listResourceByUserId(String userId);

    List<Map> listUserFunByMenu(String userId, String menuId);

    /**
     * 判断资源是否关联角色
     * @param resourceId
     * @return
     */
    boolean hasResourceRelRole(String resourceId);

}
