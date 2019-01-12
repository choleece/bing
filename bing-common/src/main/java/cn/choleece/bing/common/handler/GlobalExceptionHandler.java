package cn.choleece.bing.common.handler;

import cn.choleece.bing.common.util.LogUtil;
import cn.choleece.bing.common.util.R;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    public String defaultRestException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        LogUtil.error(logger, "global exception handler", "");

        return R.error();
    }
}
