package com.xiao.es.test;

import com.xiao.es.test.util.ESUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * 批量操作-添加
 */
public class ESTest_Bulk_Add {
    public static void main(String[] args) throws IOException {
        //获取ES客户端
        RestHighLevelClient esClient = ESUtils.getESClient();

        //获取批量操作对象
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("user").id("1").source(XContentType.JSON,
                "name","zhangsan","age","30","sex","男"));
        request.add(new IndexRequest().index("user").id("2").source(XContentType.JSON,
                "name","lisi","age","23","sex","女"));
        request.add(new IndexRequest().index("user").id("3").source(XContentType.JSON,
                "name","wangwu","age","41","sex","男"));
        request.add(new IndexRequest().index("user").id("4").source(XContentType.JSON,
                "name","json","age","30","sex","男"));
        request.add(new IndexRequest().index("user").id("5").source(XContentType.JSON,
                "name","mary","age","30","sex","女"));
        request.add(new IndexRequest().index("user").id("6").source(XContentType.JSON,
                "name","jack","age","31","sex","男"));
        //发送请求,获取响应对象
        BulkResponse responses = esClient.bulk(request, RequestOptions.DEFAULT);
        //打印信息
        System.out.println("took: " + responses.getTook());  //耗时
        System.out.println("items: " + responses.getItems());
        //关闭连接
        esClient.close();
    }
}
