package com.xiao.es.test;

import com.xiao.es.test.util.ESUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * 文档删除
 */
public class ESTest_Doc_Delete {
    public static void main(String[] args) throws IOException {
        //获取ES客户端
        RestHighLevelClient esClient = ESUtils.getESClient();

        //删除文档 - 请求对象
        DeleteRequest request = new DeleteRequest().index("user").id("1");
        //发送请求，获取响应对象
        DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);
        //响应状态
        System.out.println("操作结果： " + response.toString());
        //关闭连接
        esClient.close();
    }
}
