package org.jarvis.algorithm;

import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lovel
 * @date 2021/7/8-0:09
 */
public class WeightRandomTest {
    static List<Pair<String, Integer>> list;
    private static WeightRandom<String, Integer> random;
    private static Logger logger = LoggerFactory.getLogger(WeightRandomTest.class);


    public static void main(String[] args) {
        Map<String, Integer> countMap = new HashMap<>();
        for (int i = 0; i < 100000000; i++) {
            String randomKey = random.random();
            countMap.put(randomKey, countMap.getOrDefault(randomKey, 0) + 1);
        }

        for (Pair<String, Integer> pair : list) {
            logger.debug("{}:{}", pair.getKey(), countMap.get(pair.getKey()));
        }
    }
    static {
        list = new ArrayList<>();
        list.add(new Pair("A", 1));
        list.add(new Pair("B", 2));
        list.add(new Pair("C", 3));
        list.add(new Pair("D", 5));
        //list.add(new Pair("E", 0));

        random = new WeightRandom(list);
    }

}
