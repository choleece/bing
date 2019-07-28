package cn.choleece.bing.admin.es.dao;

import cn.choleece.bing.common.util.GsonUtil;
import cn.choleece.bing.common.util.SnowFlakeUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * base update repository
 * @author choleece
 */
public abstract class BaseRepository<T> {
    @Autowired
    public RestHighLevelClient restHighLevelClient;

    private Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * build index
     * @return
     */
    abstract IndexRequest buildIndexRequest();

    /**
     * save
     * @param o
     * @return
     * @throws IOException
     */
    public boolean save(Object o) throws IOException {
        IndexRequest request = buildIndexRequest();
        request.id(SnowFlakeUtil.getStrId());
        request.source(JSON.toJSONString(o), XContentType.JSON);
        restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return true;
    }

    /**
     * batch save
     * @param list
     * @return
     * @throws IOException
     */
    public boolean saveBatch(List list) throws IOException {
        if (list != null && !list.isEmpty()) {
            BulkRequest request = new BulkRequest();
            list.forEach(o -> request.add(buildIndexRequest().id(SnowFlakeUtil.getStrId()).source(JSON.toJSONString(o), XContentType.JSON)));
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            return true;
        }
        throw new IllegalArgumentException("The collection cannot be empty");
    }

    private SearchResponse search(SearchSourceBuilder sourceBuilder) throws IOException {
        IndexRequest request = buildIndexRequest();
        SearchRequest searchRequest = new SearchRequest(request.index());
        searchRequest.types(request.type());
        searchRequest.source(sourceBuilder);
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }

    private List<T> sourceToList(SearchResponse response) {
        List<T> list = new LinkedList<>();
        Arrays.stream(response.getHits().getHits()).forEach(i -> list.add(GsonUtil.stringToObj(i.getSourceAsString(), getTClass())));
        return list;
    }

    /**
     * list resource
     * @param sourceBuilder
     * @return
     * @throws IOException
     */
    public List<T> list(SearchSourceBuilder sourceBuilder) throws IOException {
        return sourceToList(search(sourceBuilder));
    }

    /**
     * page list resource
     * @param sourceBuilder
     * @param page
     * @return
     * @throws IOException
     */
    public IPage<T> listPage(SearchSourceBuilder sourceBuilder, IPage page) throws IOException {
        sourceBuilder.from((int) page.getCurrent());
        sourceBuilder.size((int) page.getSize());

        SearchResponse  searchResponse = search(sourceBuilder);
        page.setTotal(searchResponse.getHits().getTotalHits());
        page.setRecords(sourceToList(searchResponse));

        return page;
    }

    /**
     * update by query
     * @param updateByQueryRequest
     * @return
     * @throws IOException
     */
    public long updateByQuery(UpdateByQueryRequest updateByQueryRequest) throws IOException {
        IndexRequest indexRequest = buildIndexRequest();
        updateByQueryRequest.indices(indexRequest.indices());
        updateByQueryRequest.setDocTypes(indexRequest.type());

        BulkByScrollResponse response = restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);

        return response.getUpdated();
    }

    /**
     * delete by query
     * @param deleteByQueryRequest
     * @return
     * @throws IOException
     */
    public long deleteByQuery(DeleteByQueryRequest deleteByQueryRequest) throws IOException {
        IndexRequest indexRequest = buildIndexRequest();
        deleteByQueryRequest.indices(indexRequest.index());
        deleteByQueryRequest.setDocTypes(indexRequest.type());

        BulkByScrollResponse response = restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);

        return response.getDeleted();
    }
}
