package cn.choleece.bing.common.xss;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 * @author choleece
 * @date 2018/9/16
 */
public class XssFilter implements Filter {

    private String excludedPages;

    private String[] excludedPageArray;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedPages = filterConfig.getInitParameter("excludedPages");
        if (!StringUtils.isEmpty(excludedPages)) {
            excludedPageArray = excludedPages.split(",");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        XssHttpServletRequestWrapper xssHttpServletRequestWrapper = new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest);

        boolean isExcludePage = false;
        for (String page : excludedPageArray) {
            if (((HttpServletRequest) servletRequest).getServletPath().equals(page)) {
                isExcludePage = true;
                break;
            }
        }
        if (isExcludePage) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(xssHttpServletRequestWrapper, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    public String getExcludedPages() {
        return excludedPages;
    }

    public void setExcludedPages(String excludedPages) {
        this.excludedPages = excludedPages;
    }
}
