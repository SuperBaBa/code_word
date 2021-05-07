package org.jarvis.consumer.processors;

/**
 * author:tennyson date:2020/6/16
 **/
public abstract class AbstractProcessor {
    private AbstractProcessor nextProcessor;

    //责任链的下一个处理器
    public void setNextProcessor(AbstractProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    //转向下一个处理器进行处理
    public Object forwordNextProcess(Object obj) {
        Object result = null;
        try {
            result = this.process(obj);
        } catch (Exception e) {
            return nextProcessor.process(obj);
        }
        return result;
    }

    public abstract Object process(Object obj);
}
