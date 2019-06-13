package cn.choleece.bing.ums.controller;

import cn.choleece.bing.common.annotation.CurrentUser;
import cn.choleece.bing.common.base.BaseController;
import cn.choleece.bing.common.util.R;
import cn.choleece.bing.common.util.SnowFlakeUtil;
import cn.choleece.bing.common.vo.LoginUser;
import cn.choleece.bing.ums.entity.SysRole;
import cn.choleece.bing.ums.entity.SysRoleResource;
import cn.choleece.bing.ums.service.ISysRoleResourceService;
import cn.choleece.bing.ums.service.ISysRoleService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author choleece
 * @date 2018/11/3
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
    @Autowired
    private ISysRoleService sysSoleService;
    @Autowired
    private ISysRoleResourceService roleResourceService;

    @GetMapping("")
    public String listSysRole(@RequestParam("roleName") String roleName, @RequestParam("pageIndex") int pageIndex,
                         @RequestParam("pageSize") int pageSize) {
        Page<SysRole> page = new Page(pageIndex, pageSize);
        QueryWrapper<SysRole> query = new QueryWrapper<SysRole>().like(!StringUtils.isEmpty(roleName), "role_name", roleName);
        return R.ok(sysSoleService.page(page, query));
    }

    @PostMapping("save")
    public String saveSysRole(@RequestBody SysRole sysRole, @CurrentUser LoginUser loginUser) {
        sysRole.setCreateUser(loginUser.getUserName());
        if (StringUtils.isEmpty(sysRole.getId())) {
            QueryWrapper<SysRole> query = new QueryWrapper<SysRole>().eq("role_name", sysRole.getRoleName());
            if (sysSoleService.getOne(query) != null) {
                return R.error("角色名不能重复");
            }
            sysRole.setId(SnowFlakeUtil.getStrId());
        }
        sysSoleService.saveOrUpdate(sysRole);
        return R.ok();
    }

    /**
     * 通过角色ID获取角色信息
     * @param roleId
     * @return
     */
    @GetMapping("/{roleId}")
    public String getSysRole(@PathVariable("roleId") String roleId) {
        return R.ok(sysSoleService.getById(roleId));
    }



    /**
     * 查询角色关联资源
     * @return
     */
    @GetMapping(value="/{roleId}/resource")
    public String relResource(@PathVariable("roleId") String roleId) {
        return R.ok(roleResourceService.list(new QueryWrapper<SysRoleResource>().eq("role_id", roleId)));
    }

    /**
     * 更新角色关联资源
     * @param roleId
     * @param json
     * @return
     */
    @PostMapping(value="/{roleId}/resource")
    public String updateRoleRelResource(@PathVariable("roleId") String roleId, @RequestBody JSONObject json) {
        String resourceIdStr = json.getString("resourceId");
        String[] resourceIds = resourceIdStr.split(",");
        sysSoleService.updateRoleRelResource(roleId, resourceIds);
        return R.ok();
    }

    @PostMapping(value="/{roleId}/del")
    public String delRole(@PathVariable("roleId") String roleId) {
        if(sysSoleService.hasRoleRelUser(roleId)) {
            return R.error("角色已经关联用户，不能删除");
        }
        if (sysSoleService.hasRoleRelResource(roleId)) {
            return R.error("角色已经关联资源，不能删除");
        }
        sysSoleService.removeById(roleId);
        return R.ok();
    }
}
