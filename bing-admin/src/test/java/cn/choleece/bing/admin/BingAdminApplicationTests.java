package cn.choleece.bing.admin;

import cn.choleece.bing.common.util.PropertiesFileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BingAdminApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println(PropertiesFileUtil.getInstance().get("app.name"));
	}

	@Test
	public void testHello() {
		System.out.println("hello  world");
	}

}
