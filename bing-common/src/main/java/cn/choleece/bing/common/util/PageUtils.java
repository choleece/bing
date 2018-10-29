package cn.choleece.bing.common.util;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * sql 分页
 * @author choleece
 * @date 2018/10/29
 */
public class PageUtils implements Serializable {

    private static final long serialVersionUID = 5736323586608407530L;

    private int totalCount;

    private int pageSize;

    private int totalPage;

    private int currPage;

    private List list;

    private boolean hasMore;

    /**
     * 分页
     * @param totalCount 满足某条件的总条数
     * @param pageSize 每页的大小
     * @param currPage 当前页码
     * @param list 数据列表
     */
    public PageUtils(int totalCount, int pageSize, int currPage, List list) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
        this.currPage = currPage;
        this.list = list;
        this.hasMore = totalCount > pageSize * currPage;
    }

    public PageUtils(PageInfo pageInfo) {
        this.list = pageInfo.getList();
        this.pageSize = pageInfo.getPageSize();
        this.currPage = pageInfo.getPageNum();
        this.hasMore = pageInfo.isHasNextPage();
        this.totalCount = (int)pageInfo.getTotal();
        this.totalPage = pageInfo.getPages();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
