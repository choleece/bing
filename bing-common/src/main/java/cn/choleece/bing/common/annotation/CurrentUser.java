package cn.choleece.bing.common.annotation;

import java.lang.annotation.*;

/**
 * Created by choleece on 2018/9/15.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
    /**
     * 当前用户在request中的名字
     *
     * @return
     */
    String value() default "user";
}
