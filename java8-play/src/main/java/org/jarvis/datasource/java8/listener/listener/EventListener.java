package org.jarvis.consumer.java8.listener.listener;

import org.jarvis.consumer.java8.listener.event.IEventObject;

/**
 * 监听器模式
 *
 * @author Marcus
 * @date 2021/2/23-12:11
 * @description 监听器接口声明
 */
public interface EventListener {
    /**
     * 监听器处理事件
     *
     * @param eventObject
     */
    void handleEvent(IEventObject eventObject);
}
