package org.jarvis.consumer.java8.listener.event;

import org.jarvis.consumer.java8.listener.listenable.Listenable;

/**
 * 定义一个事件接口，事件需要做两件事
 * 1、获取事件源
 * 2、获取事件类型
 *
 * @author Marcus
 * @date 2021/2/23-12:46
 */
public interface IEventObject {
    Listenable getEventSource();
}
