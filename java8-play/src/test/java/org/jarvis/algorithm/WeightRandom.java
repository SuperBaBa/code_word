package org.jarvis.algorithm;

import javafx.util.Pair;
import org.assertj.core.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 权重随机算法
 *
 * @author Lovel
 * @date 2021/7/8-0:06
 */
public class WeightRandom<K,V extends Number>{
    private TreeMap<Double, K> weightMap = new TreeMap<>();
    private static final Logger logger = LoggerFactory.getLogger(WeightRandom.class);

    public WeightRandom(List<Pair<K, V>> list) {
        Objects.requireNonNull(list, "list can NOT be null!");
        for (Pair<K, V> pair : list) {
            Preconditions.checkArgument(pair.getValue().doubleValue() > 0, String.format("非法权重值：pair=%s", pair));
            double lastWeight = this.weightMap.size() == 0 ? 0 : this.weightMap.lastKey();//统一转为double
            this.weightMap.put(pair.getValue().doubleValue() + lastWeight, pair.getKey());//权重累加
        }
    }

    public K random() {
        double randomWeight = this.weightMap.lastKey() * Math.random();
        SortedMap<Double, K> tailMap = this.weightMap.tailMap(randomWeight, false);
        return this.weightMap.get(tailMap.firstKey());
    }
}
