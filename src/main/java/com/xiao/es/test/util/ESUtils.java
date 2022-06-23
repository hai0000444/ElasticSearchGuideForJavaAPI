package com.xiao.es.test.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ESUtils {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 9200;
    private static final String SCHEME = "http";

    public static RestHighLevelClient getESClient(){
        //ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost(HOSTNAME, PORT, SCHEME))
        );
        return esClient;
    }
}
