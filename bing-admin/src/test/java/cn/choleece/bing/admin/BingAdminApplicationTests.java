package cn.choleece.bing.admin;

import cn.choleece.bing.admin.entity.SysUser;
import cn.choleece.bing.admin.mapper.SysUserMapper;
import cn.choleece.bing.admin.service.ISysUserService;
import cn.choleece.bing.common.util.PropertiesFileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BingAdminApplicationTests {
	@Autowired
	private ISysUserService userService;
	@Autowired
	private SysUserMapper userMapper;

	@Test
	public void contextLoads() {
		System.out.println(PropertiesFileUtil.getInstance().get("app.name"));
	}

	@Test
	public void testHello() {
		try {
			SysUser user = userService.getUserByName("admin");
			System.out.println(user.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMybatisPlus() {
		System.out.println(("----- selectAll method test ------"));
		List<SysUser> userList = userMapper.selectList(null);
		userList.forEach(System.out::println);
	}

}
