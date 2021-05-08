package org.jarvis.consumer.java8.listener.listenable;

import org.jarvis.consumer.java8.listener.event.IEventObject;
import org.jarvis.consumer.java8.listener.listener.EventListener;

/**
 * 定义事件源
 *
 * @author Marcus
 * @date 2021/2/23-12:48
 * @description this is function description
 */
public interface Listenable {
    /**
     * 配置监听器
     *
     * @param eventListener
     */
    void setListener(EventListener eventListener);

    /**
     * 触发监听器
     *
     * @param eventObject
     */
    void triggerListeners(IEventObject eventObject);
}
