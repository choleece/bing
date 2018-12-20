package cn.choleece.bing.common.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseRoleMapper {

    @Select("select role_id from sys_user_role where user_id = #{userId}")
    String getRoleId(@Param("userId") String userId);

    List<String> listRolePermissions(@Param("userId") String userId);
}
