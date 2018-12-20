package cn.choleece.bing.common.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * http util
 * @author choleece
 * @date 2018/9/15
 */
public class HttpUtil {

    public static final String TOKEN_NAME = "t";

    /**
     * 根据参数名获取请求头里的数据
     * @param request
     * @param headerName
     * @return
     */
    public static String getHttpHeaderValue(ServletRequest request, String headerName) {
        return ((HttpServletRequest) request).getHeader(headerName);
    }

    /**
     * 获取当前登录用户的uid
     * @param request
     * @return
     */
    public static String getUid(HttpServletRequest request) {
        return JwtUtil.getUid(getHttpHeaderValue(request, TOKEN_NAME));
    }
}
