package cn.choleece.bing.ums.controller;

import cn.choleece.bing.common.annotation.CurrentUser;
import cn.choleece.bing.common.base.BaseController;
import cn.choleece.bing.common.util.R;
import cn.choleece.bing.common.util.SnowFlakeUtil;
import cn.choleece.bing.common.vo.LoginUser;
import cn.choleece.bing.ums.entity.SysRole;
import cn.choleece.bing.ums.entity.SysUser;
import cn.choleece.bing.ums.entity.SysUserRole;
import cn.choleece.bing.ums.service.ISysRoleService;
import cn.choleece.bing.ums.service.ISysUserRoleService;
import cn.choleece.bing.ums.service.ISysUserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/sys/user")
@Api(description = "系统用户相关接口")
public class SysUserController extends BaseController {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysUserRoleService userRoleService;

    @GetMapping("/login/user")
    public String getUserInfo(@CurrentUser LoginUser loginUser) {
        return userService.getUserInfoByUserName(loginUser.getUserName());
    }

    /**
     * 根据用户名模糊查询
     * @return
     */
    @ApiOperation(value = "系统用户分页信息")
    @GetMapping("/page")
    public String listUsers(@RequestParam("keyword") String keyword,
                       @RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize) {
        Page<SysUser> page = new Page<>(pageIndex, pageSize);
        userService.pageList(page, keyword);
        return R.ok(page);
    }

    /**
     * 根据用户名ID获取用户信息
     * @return
     */
    @GetMapping(value="/{userId}")
    public String getUserById(@PathVariable String userId) {
        SysUser user = userService.getById(userId);
        user.setSalt("");
        user.setPassword("");
        return R.ok(user);
    }

    /**
     * 增加或修改用户
     * @param sysUser
     * @param loginUser
     * @return
     */
    @PostMapping("")
    public String saveSysUser(@RequestBody SysUser sysUser, @CurrentUser LoginUser loginUser) {
        QueryWrapper<SysUser> query = new QueryWrapper<SysUser>().eq("user_name", sysUser.getUserName());
        SysUser user = userService.getOne(query);
        sysUser.setCreateUser(loginUser.getUserName());
        if (StringUtils.isEmpty(sysUser.getId())) {
            if (user != null) {
                return R.error("用户名不能重复");
            }
            sysUser.setId(SnowFlakeUtil.getStrId());
            // 新用户需要初始密码
            sysUser.setPassword("123456");
        } else {
            sysUser.setPassword(user.getPassword());
            sysUser.setSalt(user.getSalt());
        }
        sysUser.setDelStatus(0);
        sysUser.setCreateDate(new Date());
        userService.saveOrUpdate(sysUser);
        return R.ok();
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @PostMapping(value="/{userId}/del")
    public String delUser(@PathVariable("userId") String userId) {
        SysUser updateUser = new SysUser();
        updateUser.setId(userId);
        updateUser.setDelStatus(1);
        userService.updateById(updateUser);
        return R.ok();
    }

    /**
     * 重置用户密码
     * @param userId
     * @return
     */
    @PostMapping(value="/{userId}/reset/pwd")
    public String resetUserPwd(@PathVariable("userId") String userId) {
        SysUser updateUser = new SysUser();
        updateUser.setId(userId);
        updateUser.setPassword("123456");
        userService.updateById(updateUser);
        return R.ok();
    }

    /**
     * 用户组关联的角色
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/role/all")
    public String listUserRoles(@PathVariable("userId") String userId) {
        HashMap<String, Object> data = new HashMap<>();
        ArrayList<String> selectRoles = new ArrayList<>();
        List<SysRole> roles = roleService.list();
        List<SysUserRole> grs = userRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id", userId));
        if (grs != null && !grs.isEmpty()) {
            Iterator<SysUserRole> iterator = grs.iterator();
            while (iterator.hasNext()) {
                SysUserRole ur = iterator.next();
                selectRoles.add(ur.getRoleId());
            }
        }
        data.put("roles", roles);
        data.put("selectRoles", selectRoles.toArray());
        return R.ok(data);
    }

    /**
     * 用户组关联的角色
     *
     * @param userId
     * @return
     */
    @PostMapping("/{userId}/role")
    public String saveUserRoles(@PathVariable("userId") String userId, @RequestBody JSONObject json, @CurrentUser LoginUser loginUser) {
        String roleIds = json.getString("roleIds");
        List<SysUserRole> userRoles = new ArrayList<>();
        //为空时表示移出权限
        String[] roleIdArr = roleIds.split(",");
        for (String roleId : roleIdArr) {
            SysUserRole userRole = new SysUserRole();
            userRole.setId(SnowFlakeUtil.getStrId());
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRole.setAuthUserId(loginUser.getUid());
            userRoles.add(userRole);
        }
        userRoleService.saveUserRoles(userId, userRoles);
        return R.ok();
    }

}
