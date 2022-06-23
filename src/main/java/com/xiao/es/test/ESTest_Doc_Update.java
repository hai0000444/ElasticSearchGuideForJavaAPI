package com.xiao.es.test;

import com.xiao.es.test.util.ESUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * 文档(部分)更新
 */
public class ESTest_Doc_Update {
    public static void main(String[] args) throws IOException {
        //获取ES客户端
        RestHighLevelClient esClient = ESUtils.getESClient();

        //修改文档 - 请求对象
        UpdateRequest request = new UpdateRequest();
        //配置修改参数
        request.index("user").id("1");
        //设置请求体，对数据进行修改
        request.doc(XContentType.JSON,"sex","女");
        //发送请求，获取响应对象
        UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);
        //打印信息
        System.out.println("_index:" + response.getIndex());
        System.out.println("_id: " + response.getId());
        System.out.println("_result: " + response.getResult());
        //关闭连接
        esClient.close();
    }
}
