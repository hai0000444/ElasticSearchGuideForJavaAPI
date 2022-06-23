package com.xiao.es.test;

import com.xiao.es.test.util.ESUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * 批量操作-删除
 */
public class ESTest_Bulk_Delete {
    public static void main(String[] args) throws IOException {
        //获取ES客户端
        RestHighLevelClient esClient = ESUtils.getESClient();

        //获取批量操作对象
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest().index("user").id("1"));
        request.add(new DeleteRequest().index("user").id("2"));
        request.add(new DeleteRequest().index("user").id("3"));
        request.add(new DeleteRequest().index("user").id("4"));
        request.add(new DeleteRequest().index("user").id("5"));
        request.add(new DeleteRequest().index("user").id("6"));
        //发送请求,获取响应对象
        BulkResponse responses = esClient.bulk(request, RequestOptions.DEFAULT);
        //打印信息
        System.out.println("took: " + responses.getTook());  //耗时
        System.out.println("items: " + responses.getItems());
        //关闭连接
        esClient.close();
    }
}
