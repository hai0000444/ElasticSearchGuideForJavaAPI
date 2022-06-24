package com.xiao.es.test;

import com.xiao.es.test.util.ESUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * matchQuery和queryStringQuery的区别：
 * 1. 二者都可以用作筛选条件，
 * 2. 区别是：matchQuery中没有转译特殊字符，如果你的查询条件中包含特殊字符，使用matchQuery会导致结果不准确。
 * 3. queryStringQuery会识别转义符，使查询条件成为整体，不会对中文进行拆分匹配，比如：
 *      QueryBuilders.queryStringQuery("name : 美女");   会分别匹配 美 和 女
 *      QueryBuilders.queryStringQuery("name : \"美女\"");   只会匹配 美女 ，会识别转义符
 */
public class ESTest_Search_queryStringQuery {
    public static void main(String[] args) throws IOException {
        //获取ES客户端
        RestHighLevelClient esClient = ESUtils.getESClient();

        //获取搜索请求对象
        SearchRequest request = new SearchRequest().indices("user");
        //构建查询请求体

//  Lucene查询语法
//  例1. age = "30"  ,  这里 \"30\"带不带双引号都不会影响查询，只有一个值不会产生歧义
//        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery("age : \"30\"");
//  例2. AND和OR必须大写
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery("age : (30 OR 31 OR 41)");
//  例3. age != "30"
//        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery("-age : \"30\"");
//  例4. age not in ("23","41"),    AND表示既是23又是41，所以age既是23又是41不存在，会查全量
//        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery("-age : (\"23\" OR \"41\")");
//        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery("-age : (\"23\" AND \"41\")");

//  例5. 创建查询对象,查询 所有字段中 含有changge 且 不含有hejiu的文档
//        QueryBuilder qb = QueryBuilders.queryStringQuery("+changge -hejiu");
//  例6. 创建查询对象,查询 所有字段中 含有changge 或者 不含有hejiu的文档
//        QueryBuilder qb = QueryBuilders.simpleQueryStringQuery("+changge -hejiu");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(queryStringQueryBuilder);
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
