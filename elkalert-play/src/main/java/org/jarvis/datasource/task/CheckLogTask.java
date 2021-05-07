package org.jarvis.consumer.task;

import com.alibaba.fastjson.JSONArray;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.jarvis.consumer.listener.ESResponseActionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

/**
 * author:tennyson date:2020/6/16
 **/
@Configuration
public class CheckLogTask {
    @Value("${indies:search_waybill_log}")
    private String[] indices;
    @Autowired
    @Qualifier(value = "ignoreKeyword")
    private HashMap<String, JSONArray> ignoreKeyword;
    @Qualifier("initElasticsearchClient")
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ESResponseActionListener responseActionListener;
    @Value(value = "${recentLongTime:1}")
    private String recentLongTime;

    //3.添加定时任务
    @Scheduled(cron = "${cron.expression}")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        LocalDateTime startTime = LocalDateTime.now().minusHours(Long.parseLong(recentLongTime));
        LocalDateTime endTime = LocalDateTime.now();
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("@timestamp")
                .gte(startTime.toInstant(ZoneOffset.of("+8")).toEpochMilli())
                .lte(endTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("logLevel", "ERROR"))
                .filter(rangeQueryBuilder);
        this.addFilterKeyword(boolQueryBuilder);
        QueryBuilder queryBuilder = QueryBuilders.constantScoreQuery(boolQueryBuilder);
        String[] includes = {"appId", "hostName", "logLevel", "message", "packageName", "threadName", "@timestamp"};
        String[] excludes = {"_id", "_index"};
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.size(500);
        sourceBuilder.query(queryBuilder).fetchSource(includes, excludes);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(sourceBuilder);
        searchRequest.indices(indices);
        restHighLevelClient.searchAsync(searchRequest, RequestOptions.DEFAULT, responseActionListener);
        System.err.printf("执行静态定时任务时间: 从%s开始查询到%s\n", startTime, endTime);
    }

    /**
     * filter context 更改为 match_phrase
     * @param boolQueryBuilder 分组查询builder
     * @return
     */
    public BoolQueryBuilder addFilterKeyword(BoolQueryBuilder boolQueryBuilder) {
        BoolQueryBuilder baseBoolQuery=boolQueryBuilder;
        if (!ignoreKeyword.isEmpty()) {
            for (String field : ignoreKeyword.keySet()) {
                Object[] valueArray = ignoreKeyword.get(field).toArray();
                for (Object o : valueArray) {
                    baseBoolQuery.mustNot(QueryBuilders.matchPhraseQuery(field, o));
                }
            }
        }
        return baseBoolQuery;
    }
}
