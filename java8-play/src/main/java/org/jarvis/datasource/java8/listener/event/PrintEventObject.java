package org.jarvis.consumer.java8.listener.event;

import org.jarvis.consumer.java8.listener.listenable.Listenable;

/**
 * <一句话概述功能>
 *
 * @author Marcus
 * @date 2021/2/23-12:43
 * @description this is function description
 */
public class PrintEventObject implements org.jarvis.consumer.java8.listener.event.IEventObject {
    // 事件源
    private Listenable eventSource;

    @Override
    public Listenable getEventSource() {
        return eventSource;
    }
}
