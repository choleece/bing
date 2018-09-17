package cn.choleece.bing.common.annotation;

import java.lang.annotation.*;

/**
 * Created by choleece on 2018/9/15.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}
