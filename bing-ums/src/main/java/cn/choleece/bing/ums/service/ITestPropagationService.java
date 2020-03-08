package cn.choleece.bing.ums.service;

/**
 * @author choleece
 * @Description: TODO
 * @Date 2020-02-01 13:59
 **/
public interface ITestPropagationService {

    /****************************** propagation required ***************************/

    void testPropagationRequired();

    void testPropagationRequiredTwo();

    void testPropagationRequiredWithException();

    void testPropagationRequiredWithoutTransaction();

    void testPropagationRequiredWithTransaction();

    /****************************** propagation new ***************************/

    void testPropagationNew();

    void testPropagationNewWithException();

    /****************************** propagation new ***************************/

    void testPropagationNested();

    void testPropagationNestedWithException();
}
