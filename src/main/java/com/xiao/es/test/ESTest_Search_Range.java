package com.xiao.es.test;

import com.xiao.es.test.util.ESUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * 范围查询
 */
public class ESTest_Search_Range {
    public static void main(String[] args) throws IOException {
        //获取ES客户端
        RestHighLevelClient esClient = ESUtils.getESClient();

        //获取搜索请求对象
        SearchRequest request = new SearchRequest().indices("user");
        //构建查询请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //构建range查询请求体,根据age字段
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");

        /*
        //大于   Greater Than
        rangeQueryBuilder.gt("25");
        //小于   Less Than
        rangeQueryBuilder.lt("35");
        */

        //大于等于,Greater Than or Equal to
        rangeQueryBuilder.gte("30");
        //小于等于,Less Than or Equal to
        rangeQueryBuilder.lte("40");

        sourceBuilder.query(rangeQueryBuilder);

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
