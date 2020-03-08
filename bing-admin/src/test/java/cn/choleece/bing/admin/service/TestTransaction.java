package cn.choleece.bing.admin.service;

import cn.choleece.bing.ums.service.ITestPropagationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author choleece
 * @Description: TODO
 * @Date 2020-02-01 14:19
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTransaction {

    @Autowired
    private ITestPropagationService testPropagationService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testPropagationRequiredWithoutTransactionWithOutTransaction() {
        testPropagationService.testPropagationRequiredWithoutTransaction();
    }
}
