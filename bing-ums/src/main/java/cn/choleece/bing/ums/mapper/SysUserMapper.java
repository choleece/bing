package cn.choleece.bing.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.choleece.bing.ums.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author choleece
 * @date 2018/9/26
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户名取用户信息
     * @param userName
     * @return
     */
    SysUser getUserInfoByUserName(String userName);

    /**
     * 分页查询
     * @param page
     * @param keyword
     * @return
     */
    List<SysUser> userPageList(Page page, @Param("keyword") String keyword);

}
