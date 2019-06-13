package cn.choleece.bing.common.datasource.dynamic.aspect;

import cn.choleece.bing.common.datasource.dynamic.annotation.DataSource;
import cn.choleece.bing.common.datasource.dynamic.config.DynamicContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *
 * @author choleece
 * @date 2019/4/28
 */
@Component
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DataSourceAspect {

    private static final Logger logger = LogManager.getLogger(DataSourceAspect.class);

    @Pointcut("@annotation(cn.choleece.bing.common.datasource.dynamic.annotation.DataSource) " +
            "|| @within(cn.choleece.bing.common.datasource.dynamic.annotation.DataSource)")
    public void dataSourcePointCut() {}

    @Around("dataSourcePointCut()")
    public Object round(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Class targetClass = joinPoint.getTarget().getClass();
        Method method = signature.getMethod();

        DataSource targetDataSource = (DataSource) targetClass.getAnnotation(DataSource.class);
        DataSource methodDataSource = method.getAnnotation(DataSource.class);

        if (targetDataSource != null || methodDataSource != null) {
            String value;
            if (methodDataSource != null) {
                value = methodDataSource.value();
            } else {
                value = targetDataSource.value();
            }

            logger.info("数据源切换到{}", value);
            DynamicContextHolder.push(value);
        }

        try {
            return joinPoint.proceed();
        } finally {
            DynamicContextHolder.poll();
        }
    }
}
