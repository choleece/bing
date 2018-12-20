package cn.choleece.bing.common.jwt;

import cn.choleece.bing.common.util.*;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt 过滤器
 * @author choleece
 * @date 2018/9/15
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    protected boolean isLoginAttempt(ServletRequest request) {
        return null != HttpUtil.getHttpHeaderValue(request, HttpUtil.TOKEN_NAME);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        String token = HttpUtil.getHttpHeaderValue(request, HttpUtil.TOKEN_NAME);
        return JwtUtil.verify(token, JwtUtil.JWT_USER_KEY);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        boolean loggedIn = false;
        if (isLoginAttempt(request)) {
            loggedIn = executeLogin(request, response);
        }
        if (!loggedIn) {
            response401(response);
        }
        return loggedIn;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /index/401
     */
    private void response401(ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.sendRedirect(PropertiesFileUtil.getInstance().get("401.url"));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 将非法请求跳转到 /index/403
     */
    private void response403(ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.sendRedirect(PropertiesFileUtil.getInstance().get("403.url"));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
