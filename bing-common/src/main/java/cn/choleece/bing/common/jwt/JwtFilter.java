package cn.choleece.bing.common.jwt;

import cn.choleece.bing.common.util.HttpUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt 过滤器
 * @author choleece
 * @date 2018/9/15
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private static final String HEADER_NAME = "token";

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        return null != HttpUtil.getHttpHeaderValue(request, HEADER_NAME);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        // 当登录失败，会抛出异常
        getSubject(request, response).login(new JwtToken(HttpUtil.getHttpHeaderValue(request, HEADER_NAME)));
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                response401(response);
            }
        }
        return true;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return super.onPreHandle(request, response, mappedValue);
    }

    private void response401(ServletResponse response) {
        try {
            ((HttpServletResponse) response).sendRedirect("/401");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
