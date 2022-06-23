package com.xiao.es.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiao.es.test.pojo.User;
import com.xiao.es.test.util.ESUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * 文档新增
 */
public class ESTest_Doc_Add {
    public static void main(String[] args) throws IOException {
        //获取ES客户端
        RestHighLevelClient esClient = ESUtils.getESClient();

        //新增文档 - 请求对象
        IndexRequest request = new IndexRequest();
        //设置索引及唯一性标识
        request.index("user").id("1");
        //创建数据对象
        User user = new User();
        user.setName("张三");
        user.setAge(32);
        user.setSex("男");
        //转为JSON
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        //添加文档数据，JSON格式
        request.source(userJson, XContentType.JSON);
        //发送请求,获取响应对象
        IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
        //打印信息
        System.out.println("_index: " + response.getIndex());
        System.out.println("_id: " + response.getId());
        System.out.println("_result: " + response.getResult());

        //关闭连接
        esClient.close();
    }
}
