package com.xiao.es.test;

import com.xiao.es.test.util.ESUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * 匹配查询,带分词功能的全文搜索
 * 1. String字段可以分词，也可以不分词，默认为分词
 * 2. 分词的时候，默认的标准分析器可以将一句话中的单词划分开，然后转为小写
 *    比如：Quick Brown Fox!，标准分析器将会将它转换为quick , brown , fox 。 此时不再能进行“Quick Brown Fox!”的完整匹配
 * 3. matchQuery搜索的时候，首先会解析查询字符串，进行分词，然后查询，
 *    例如：QueryBuilders.matchQuery("name", "Mike Jackson");   分别按Mike和Jackson匹配
 */
public class ESTest_Search_matchQuery {
    public static void main(String[] args) throws IOException {
        //获取ES客户端
        RestHighLevelClient esClient = ESUtils.getESClient();

        //获取搜索请求对象
        SearchRequest request = new SearchRequest().indices("user");
        //构建请求体,matchQuery是带分词功能的全文搜索
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("age", 30);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(matchQueryBuilder);
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
