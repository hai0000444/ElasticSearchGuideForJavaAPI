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
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * 聚合查询
 */
public class ESTest_Search_Aggregation {
    public static void main(String[] args) throws IOException {
        //获取ES客户端
        RestHighLevelClient esClient = ESUtils.getESClient();

        //获取搜索请求对象
        SearchRequest request = new SearchRequest().indices("user");
        //构建查询请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //设置聚合查询条件，aggregation
        //查询age的最大值，并命名为maxAge
        sourceBuilder.aggregation(AggregationBuilders.max("maxAge").field("age"));
        //查询age的最小值，并命名为minAge
        sourceBuilder.aggregation(AggregationBuilders.min("minAge").field("age"));
        //查询age的平均值，并命名为avgAge
        sourceBuilder.aggregation(AggregationBuilders.avg("avgAge").field("age"));

        //分组统计
        TermsAggregationBuilder termsAggBuilder = AggregationBuilders.terms("age_groupBy").field("age");
        sourceBuilder.aggregation(termsAggBuilder);

        request.source(sourceBuilder);
        //发送请求，查询所有数据
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //查询匹配
        System.out.println(response);
        //关闭连接
        esClient.close();
    }
}
