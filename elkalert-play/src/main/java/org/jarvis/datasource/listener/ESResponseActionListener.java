package org.jarvis.consumer.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.jarvis.consumer.alert.DingTalkAlert;
import org.jarvis.consumer.processors.ArrayListProcessor;
import org.jarvis.consumer.processors.StringProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author:tennyson date:2020/6/16
 **/
@Component
public class ESResponseActionListener implements ActionListener<SearchResponse> {
    @Autowired
    private DingTalkAlert dingTalkAlert;

    private Set<String> ignoreKewordSet;

    private Map<String, ArrayList<String>> collectError = new HashMap<>();

    private final static Logger logger = LoggerFactory.getLogger(ESResponseActionListener.class);


    @Override
    public void onResponse(SearchResponse searchResponse) {
        SearchHit[] hits = searchResponse.getHits().getHits();
        if (hits.length > 0) {
            List<SearchHit> vaildHit = Arrays.stream(hits)
                    .filter(hit -> !ignoreKewordSet.contains(hit.getSourceAsString()))
                    .collect(Collectors.toList());
            StringProcessor stringProcessor = new StringProcessor();
            ArrayListProcessor arrayProcessor = new ArrayListProcessor();
            stringProcessor.setNextProcessor(arrayProcessor);
            arrayProcessor.setNextProcessor(stringProcessor);
            for (SearchHit documentFields : vaildHit) {
                Map<String, Object> sourceMap = documentFields.getSourceAsMap();
                String message = (String) arrayProcessor.forwordNextProcess(sourceMap.get("message"));
                String outPutmessage=message.length()>256?message.substring(0,256):message;
                LocalDateTime time = LocalDateTime.parse((String) sourceMap.get("@timestamp"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
                String appId = String.valueOf(sourceMap.get("appId"));
                logger.info("logTime={},appId={},reason={},costTime={}",
                        time,
                        appId,
                        outPutmessage,
                        searchResponse.getTook());
                packErrorInfomation(appId, outPutmessage);
            }
            if (!collectError.isEmpty())
                dingTalkAlert.sendMessage(collectError, (result, condition) -> {
                    Map<String, ArrayList<String>> resultThroughFilter = new HashMap<>();
                    for (String appId : result.keySet()) {
                        if (condition.containsKey(appId) && result.get(appId).size() < condition.get(appId)) {
                            continue;
                        }
                        resultThroughFilter.put(appId, result.get(appId));
                    }
                    return resultThroughFilter;
                });
            collectError.clear();
        }

    }

    @Override
    public void onFailure(Exception e) {
        System.err.println(e.getMessage());
    }

    public Map<String, ArrayList<String>> packErrorInfomation(String appId, String message) {
        if (!collectError.containsKey(appId)) {
            collectError.put(appId, new ArrayList<>(Collections.singletonList(message)));
        }
        collectError.get(appId).add(message);
        return collectError;
    }

    @Autowired
    public void initIgnoreKeyword(@Value("${ignoreKeyword:\"\"}") String ignoreKeyword) {
        this.ignoreKewordSet = new HashSet<>(Arrays.asList(ignoreKeyword.split(";")));
    }

    @FunctionalInterface
    public interface Filter {
        Map<String, ArrayList<String>> filterInvaildAlert(Map<String, ArrayList<String>> result, Map<String, Long> condition);
    }
}
