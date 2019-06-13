package cn.choleece.bing.ums.service.impl;

import cn.choleece.bing.common.constant.CommonResponseMsg;
import cn.choleece.bing.common.util.JwtUtil;
import cn.choleece.bing.common.util.R;
import cn.choleece.bing.common.vo.LoginUser;
import cn.choleece.bing.ums.dto.SysUserDto;
import cn.choleece.bing.ums.entity.SysUser;
import cn.choleece.bing.ums.mapper.SysUserMapper;
import cn.choleece.bing.ums.service.ISysUserService;
import cn.choleece.bing.ums.vo.SysUserVO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author choleece
 * @date 2018/9/26
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    private SysUserMapper userMapper;

    /**
     * 添加用户默认密码， 默认密码为123456，前端登录时，传过来的密码是经过MD5加密后的密码
     */
    private static final String DEFAULT_PASSWORD = "123456";

    @Override
    public String login(String userName, String pwd, HttpServletRequest request) {
        if (StringUtils.isBlank(userName)) {
            return R.error(CommonResponseMsg.INCORRECT_PASSWORD);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(userName, pwd);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error(e.getMessage());
        } catch (LockedAccountException e) {
            return R.error(e.getMessage());
        }
        // 执行token或者其他什么策略
        LoginUser loginUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        return R.ok(JwtUtil.sign(loginUser));
    }

    @Override
    public String getUserInfoByUserName(String userName) {
        SysUser user = userMapper.getUserInfoByUserName(userName);
        SysUserVO userVO = new SysUserVO();
        userVO.setUserName(user.getUserName());
        userVO.setRoleList(user.getRoleList());
        return R.ok(userVO);
    }

    @Override
    public Page<SysUser> pageList(Page page, String keyword) {
        List<SysUser> list = userMapper.userPageList(page, keyword);
        List<SysUserDto> resultList = new ArrayList<>(20);
        SysUserDto dto;
        for(SysUser sysUser : list){
            dto = JSONObject.parseObject(JSONObject.toJSONString(sysUser), SysUserDto.class);
            resultList.add(dto);
        }
        page.setRecords(resultList);
        return page;
    }


}
