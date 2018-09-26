package cn.choleece.bing.admin;

import cn.choleece.bing.admin.entity.SysUser;
import cn.choleece.bing.admin.service.IUserService;
import cn.choleece.bing.common.util.PropertiesFileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BingAdminApplicationTests {
	@Autowired
	private IUserService userService;

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

}
