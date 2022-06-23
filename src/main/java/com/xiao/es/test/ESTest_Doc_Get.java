package com.xiao.es.test;

import com.xiao.es.test.util.ESUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * 文档查询
 */
public class ESTest_Doc_Get {
    public static void main(String[] args) throws IOException {
        //获取ES客户端
        RestHighLevelClient esClient = ESUtils.getESClient();

        //查询文档 - 请求对象
        GetRequest request = new GetRequest().index("user").id("1");
        //发送请求
        GetResponse response = esClient.get(request, RequestOptions.DEFAULT);
        //打印查询信息
        System.out.println("_index: " + response.getIndex());
        System.out.println("_id: " + response.getId());
        System.out.println("source: " + response.getSource());
        //关闭连接
        esClient.close();
    }
}
