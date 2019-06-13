package cn.choleece.bing.ums.mapper;

import cn.choleece.bing.ums.entity.SysResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 系统资源
 * @author choleece
 *
 */
@Repository
public interface SysResourceMapper extends BaseMapper<SysResource> {

	/**
	 * 根据用户id获取该用户拥有的资源
	 * @param userId
	 * @return
	 */
	List<Map> listResourceByUserId(String userId);

	/**
	 * 根据用户id获取该用户拥有的菜单功能
	 * @param userId
	 * @param menu
	 * @return
	 */
	List<Map> listUserFunByMenu(@Param("userId") String userId, @Param("menu") String menu);

	/**
	 * 获取用户的权限列表
	 * @param userId
	 * @return
	 */
	List<String> listUserPermissions(String userId);
    
}
