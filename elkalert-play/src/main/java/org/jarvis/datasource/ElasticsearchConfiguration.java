package org.jarvis.consumer;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author:tennyson date:2020/6/16
 **/
@Configuration
public class ElasticsearchConfiguration {
    @Value(value = "${elasticsearch.urls}")
    private String urls;

    @PostConstruct
    @Bean(name = "initElasticsearchClient")
    public RestHighLevelClient initElasticsearchClient() {
        List<String> collect = Stream.of(urls.split(";")).collect(Collectors.toList());
        List<HttpHost> httpHostList = new ArrayList<>();
        for (String url : collect) {
            String host = url.split(":")[0];
            String port = url.split(":")[1];
            httpHostList.add(new HttpHost(host, Integer.parseInt(port), "http"));
        }
        return new RestHighLevelClient(
                RestClient.builder(httpHostList.toArray(new HttpHost[httpHostList.size()])));
    }
}
