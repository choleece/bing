package cn.choleece.bing.ums.service.impl;

import cn.choleece.bing.ums.entity.SysUser;
import cn.choleece.bing.ums.mapper.SysUserMapper;
import cn.choleece.bing.ums.service.ITestPropagationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author choleece
 * @Description: TODO
 * @Date 2020-02-01 13:59
 **/
@Service
public class TestPropagationServiceImpl implements ITestPropagationService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void testPropagationRequired() {
        SysUser user = new SysUser();
        user.setId("2");
        user.setUserName("张三");

        sysUserMapper.insert(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void testPropagationRequiredTwo() {
        SysUser user = new SysUser();
        user.setId("3");
        user.setUserName("李四");

        sysUserMapper.insert(user);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @Override
    public void testPropagationRequiredWithException() {
        SysUser user = new SysUser();
        user.setId("4");
        user.setUserName("王五");

        sysUserMapper.insert(user);

        throw new RuntimeException();
    }

    /**
     * 执行用例
     */
    @Override
    public void testPropagationRequiredWithoutTransaction() {
        testPropagationRequired();
        testPropagationRequiredTwo();

        testPropagationRequiredWithException();

        throw new RuntimeException();
    }

    @Override
    public void testPropagationRequiredWithTransaction() {
        testPropagationRequired();
        testPropagationRequiredWithException();
    }

    @Override
    public void testPropagationNew() {

    }

    @Override
    public void testPropagationNewWithException() {

    }

    @Override
    public void testPropagationNested() {

    }

    @Override
    public void testPropagationNestedWithException() {

    }
}
