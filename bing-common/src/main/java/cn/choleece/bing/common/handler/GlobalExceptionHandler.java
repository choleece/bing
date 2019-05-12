package cn.choleece.bing.common.handler;

import cn.choleece.bing.common.util.LogUtil;
import cn.choleece.bing.common.util.R;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理
 * @author choleece
 * @Date 2018/9/21 11:31
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public String defaultRestException(Exception ex) {
        ex.printStackTrace();
        LogUtil.error(logger, "global exception handler", ex.getMessage());

        return R.error();
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public String unAuthorizedException(UnauthorizedException e) {
        return R.error(401, "登录超时，请重新登录");
    }

    @ExceptionHandler(value = UnauthenticatedException.class)
    public String unauthenticatedException(UnauthorizedException e) {
        return R.error(403, "没有权限访问，请联系管理员");
    }
}
