package org.jarvis.consumer.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author:tennyson date:2020/6/16
 **/
@Configuration
//@RefreshScope
public class FilterConditionConfig {
    @Bean(value = "getConditionJSON")
    public Map<String, Long> getConditionJSON(@Value("${conditionJSON:''}") String conditionJSON) {
        Map<String, Long> conditionMap = new ConcurrentHashMap<>();
        try {
            if (!conditionJSON.isEmpty()) {
                JSONParser jsonParser = new JSONParser(conditionJSON);
                ArrayList<Object> mapArrayList = jsonParser.parseArray();
                for (Object object : mapArrayList) {
                    LinkedHashMap linkedHashMap = (LinkedHashMap) object;
                    Set<Map.Entry> entrySet = linkedHashMap.entrySet();
                    for (Map.Entry entry : entrySet) {
                        conditionMap.put((String) entry.getKey(), Long.parseLong((String) entry.getValue()));
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return conditionMap;
    }

    @Bean(value = "ignoreKeyword")
    public HashMap<String, JSONArray> getIgnoreKeyword(@Value("${ignoreKeyword:''}") String ignoreKeyword) {
        HashMap<String, JSONArray> conditionMap = null;
        if (!ignoreKeyword.isEmpty()) {
            conditionMap = JSON.parseObject(ignoreKeyword, HashMap.class);
        }
        return conditionMap;
    }
}
