package cn.choleece.bing.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author choleece
 * @date 2018-09-16
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.choleece.bing")
@MapperScan(basePackages = "cn.choleece.bing.*.mapper")
public class BingAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(BingAdminApplication.class, args);
	}
}
