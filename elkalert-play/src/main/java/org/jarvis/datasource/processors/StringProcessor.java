package org.jarvis.consumer.processors;

/**
 * author:tennyson date:2020/6/16
 **/
public class StringProcessor extends AbstractProcessor {
    @Override
    public String process(Object obj) {
        return (String) obj;
    }
}
