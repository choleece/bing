package cn.choleece.bing.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * http util
 * @author choleece
 * @date 2018/9/15
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 根据参数名获取请求头里的数据
     * @param request
     * @param headerName
     * @return
     */
    public static String getHttpHeaderValue(ServletRequest request, String headerName) {
        return ((HttpServletRequest) request).getHeader(headerName);
    }
}
