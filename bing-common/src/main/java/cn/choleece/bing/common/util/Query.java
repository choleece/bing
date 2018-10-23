package cn.choleece.bing.common.util;

import cn.choleece.bing.common.xss.SqlFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一处理mysql查询条件
 * @author choleece
 * @date 2018/10/18
 */
public class Query extends HashMap<String, Object> {

    private static final long serialVersionUID = 1521261705328787883L;

    private int pageIndex = 1;

    private int pageSize = 20;

    public Query(Map<String, String> map) {
        this.putAll(map);

        this.pageIndex = Integer.valueOf(map.get("pageIndex").toString());
        this.pageSize = Integer.valueOf(map.get("pageSize").toString());

        this.put("offset", (pageIndex - 1) * pageSize);
        this.put("page", pageIndex);
        this.put("limit", pageSize);

        String sdix = map.get("sidx").toString();
        String order = map.get("order").toString();
        this.put("sidx", SqlFilter.sqlInject(sdix));
        this.put("order", SqlFilter.sqlInject(order));
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
