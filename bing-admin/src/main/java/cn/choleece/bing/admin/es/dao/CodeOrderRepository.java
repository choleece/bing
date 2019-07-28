package cn.choleece.bing.admin.es.dao;

import cn.choleece.bing.admin.es.entity.CodeOrder;
import cn.choleece.bing.admin.es.index.CodeOrderIndex;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.stereotype.Component;

/**
 * @author choleece
 */
@Component
public class CodeOrderRepository extends BaseRepository<CodeOrder> {

    @Override
    IndexRequest buildIndexRequest() {
        return new IndexRequest(CodeOrderIndex.INDEX, CodeOrderIndex.TYPE);
    }
}
