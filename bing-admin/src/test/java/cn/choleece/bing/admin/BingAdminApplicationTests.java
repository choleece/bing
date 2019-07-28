package cn.choleece.bing.admin;

import cn.choleece.bing.admin.es.dao.CodeOrderRepository;
import cn.choleece.bing.admin.es.entity.CodeOrder;
import cn.choleece.bing.common.util.SnowFlakeUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.ScriptQueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BingAdminApplicationTests {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private CodeOrderRepository codeOrderRepository;

    @Test
    public void testAddDocumentToIndex() {
        IndexRequest request = new IndexRequest();
        request.index("twitter");
        request.type("_doc");
        request.id("2");
        Map<String, String> source = new HashMap<>();
        source.put("user", "choleece");
        source.put("post_date", "2019-07-27T14:00:00");
        source.put("message", "你好啊");
        request.source(source);
        try {
            IndexResponse result = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchElasticSearch() {
        SearchRequest searchRequest = new SearchRequest("twitter");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(QueryBuilders.fuzzyQuery("message", "sear"));
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        // 分页, 默认从0开始
        sourceBuilder.from(1);
        sourceBuilder.size(1);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(response.getHits().getHits());
            Arrays.stream(response.getHits().getHits())
                    .forEach(i -> {
                        System.out.println(i.getIndex());
                        System.out.println(i.getType());
                        System.out.println(i);
                        System.out.println(i.getSourceAsString());
                    });
            System.out.println(response.getHits().totalHits);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSave() {
        CodeOrder codeOrder = new CodeOrder();
        codeOrder.setId(SnowFlakeUtil.getStrId());
        codeOrder.setCode("123456");
        codeOrder.setDesc("测试描述");
        codeOrder.setOrderId(SnowFlakeUtil.getStrId());
        codeOrder.setStatus(0);
        codeOrder.setCreateTime(DateUtil.now());
        codeOrder.setOptTime(System.currentTimeMillis());

        try {
            codeOrderRepository.save(codeOrder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveBatch() {
        List<CodeOrder> codeOrders = new LinkedList<>();

        for (int i = 10; i < 20; i++) {
            CodeOrder codeOrder = new CodeOrder();
            codeOrder.setId(SnowFlakeUtil.getStrId());
            codeOrder.setCode("随便说点" + i);
            codeOrder.setDesc("测试描述" + i);
            codeOrder.setOrderId(SnowFlakeUtil.getStrId());
            codeOrder.setStatus(0);
            codeOrder.setCreateTime(DateUtil.now());
            codeOrder.setOptTime(System.currentTimeMillis());

            codeOrders.add(codeOrder);
        }

        try {
            codeOrderRepository.saveBatch(codeOrders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testList() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(QueryBuilders.matchQuery("code", "1234561"));
//        sourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery()));
//        sourceBuilder.query(QueryBuilders.boolQuery().filter(QueryBuilders.rangeQuery("code").gte("1234561").lte("1234563")));
        sourceBuilder.query(QueryBuilders.wildcardQuery("code", "*7*"));
        sourceBuilder.sort(new FieldSortBuilder("optTime").order(SortOrder.ASC));
        try {
            System.out.println(codeOrderRepository.list(sourceBuilder));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testListPage() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        IPage<CodeOrder> page = new Page<>(0, 2);
        try {
            codeOrderRepository.listPage(sourceBuilder, page);
            System.out.println(page.getCurrent());
            System.out.println(page.getPages());
            System.out.println(page.getSize());
            System.out.println(page.getTotal());
            System.out.println(page.getRecords());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateByQuery() {
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
        updateByQueryRequest.setQuery(QueryBuilders.wildcardQuery("code", "*code*"));
        updateByQueryRequest.setScript(new Script("ctx._source.code = '我是测试code'"));
        // 设置最大更新的数量
//        updateByQueryRequest.setSize(1);

        try {
            codeOrderRepository.updateByQuery(updateByQueryRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteByQuery() {
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest();
        deleteByQueryRequest.setQuery(QueryBuilders.wildcardQuery("code", "*code*"));

        try {
            codeOrderRepository.deleteByQuery(deleteByQueryRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
