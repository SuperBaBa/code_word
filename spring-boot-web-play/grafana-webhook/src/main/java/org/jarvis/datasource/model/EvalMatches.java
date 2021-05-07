package org.jarvis.consumer.model;

/**
 * @author marcus
 * @date 2020/8/19-14:27
 */
public class EvalMatches {
    private int value;
    private String metric;
    private Tags tags;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

}
