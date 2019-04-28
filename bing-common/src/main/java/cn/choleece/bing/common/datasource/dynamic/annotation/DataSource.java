package cn.choleece.bing.common.datasource.dynamic.annotation;

import java.lang.annotation.*;

/**
 * 多数据源注解
 * Created by choleece on 2019/4/28.
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    String value() default "";
}
