package com.xiao.es.test;

import com.xiao.es.test.util.ESUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * 多条件查询
 */
public class ESTest_Search_termsQuery {
    public static void main(String[] args) throws IOException {
        //获取ES客户端
        RestHighLevelClient esClient = ESUtils.getESClient();

        //获取搜索请求对象
        SearchRequest request = new SearchRequest().indices("user");
        //构建查询请求体,termsQuery, 不带分词功能的精确匹配
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termsQuery("name","zhangsan","lisi","wangwu"));

        request.source(sourceBuilder);
        //发送请求，查询所有数据
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took: " + response.getTook());
        System.out.println("timeout: " + response.isTimedOut());
        System.out.println("total: " + hits.getTotalHits());
        System.out.println("MaxScore: " + hits.getMaxScore());
        System.out.println("hits=================>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<=====================");
        //关闭连接
        esClient.close();
    }
}
