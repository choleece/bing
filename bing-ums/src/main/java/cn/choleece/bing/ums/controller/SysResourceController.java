package cn.choleece.bing.ums.controller;

import cn.choleece.bing.common.annotation.CurrentUser;
import cn.choleece.bing.common.base.BaseController;
import cn.choleece.bing.common.util.R;
import cn.choleece.bing.common.util.SnowFlakeUtil;
import cn.choleece.bing.common.vo.LoginUser;
import cn.choleece.bing.ums.entity.SysResource;
import cn.choleece.bing.ums.service.ISysResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author choleece
 */
@RestController
@RequestMapping("/sys/resource")
public class SysResourceController extends BaseController {
    @Autowired
    private ISysResourceService resourceService;

    /**
     * 查询用户拥有的资源
     * @param user
     * @return
     */
    @RequestMapping("/user")
    public String findResourceByUserId(@CurrentUser LoginUser user) {
        return R.ok(resourceService.listResourceByUserId(user.getUid()));
    }

    /**
     * 根据父资源查询子资源及本资源
     * @param resourceId
     * @return
     */
    @GetMapping("/menus/parent")
    public String findMenusByParent(@RequestParam("resourceId") String resourceId) {
        ArrayList<SysResource> list = new ArrayList<>();
        list.add(resourceService.getById(resourceId));
        list.addAll(resourceService.list(new QueryWrapper<SysResource>().eq("parent_id", resourceId).eq("del_status", 0)));
        return R.ok(list);
    }

    /**
     * 通过父ID加载其子节点
     * @param parentId
     * @return
     */
    @GetMapping("/menus/parent/{parentId}")
    public String findChildMenusByParentId(@PathVariable("parentId") String parentId) {
        return R.ok(resourceService.list(new QueryWrapper<SysResource>().eq("parent_id", parentId).eq("del_status", 0)));
    }

    /**
     * 查询所有资源
     * @return
     */
    @GetMapping("/all")
    public String listAllResources() {
        return R.ok(resourceService.list(new QueryWrapper<SysResource>().eq("del_status", 0)));
    }

    /**
     * 删除资源
     * @param resourceId
     * @return
     */
    @PostMapping("/{resourceId}/del")
    public String del(@PathVariable("resourceId") String resourceId) {
        SysResource resource = resourceService.getById(resourceId);
        if (resource == null) {
            return R.error("未找到资源");
        }
        if(resourceService.hasResourceRelRole(resourceId)) {
            return R.error("资源已经被角色关联，不能删除");
        }
        SysResource sysResource = new SysResource();
        sysResource.setId(resourceId);
        sysResource.setDelStatus(1);
        resourceService.updateById(sysResource);
        return R.ok();
    }

    /**
     * 获取资源信息
     * @param resourceId
     * @return
     */
    @GetMapping("/{resourceId}")
    public String getResourceById(@PathVariable("resourceId") String resourceId) {
        return R.ok(resourceService.getById(resourceId));
    }

    /**
     * 保存或更新资源信息
     * @param sysResource
     * @param loginUser
     * @return
     */
    @PostMapping("")
    public String saveResource(@RequestBody SysResource sysResource, @CurrentUser LoginUser loginUser) {
        sysResource.setCreateUser(loginUser.getUserName());
        if (StringUtils.isEmpty(sysResource.getId())) {
            sysResource.setId(SnowFlakeUtil.getStrId());
            sysResource.setCreateDate(new Date());
        }
        resourceService.saveOrUpdate(sysResource);
        return R.ok();
    }

}
