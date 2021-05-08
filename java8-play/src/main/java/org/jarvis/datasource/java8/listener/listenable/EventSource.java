package org.jarvis.consumer.java8.listener.listenable;

import org.jarvis.consumer.java8.listener.event.IEventObject;
import org.jarvis.consumer.java8.listener.listener.EventListener;

/**
 * <一句话概述功能>
 *
 * @author Marcus
 * @date 2021/2/23-12:44
 * @description this is function description
 */
public class EventSource implements org.jarvis.consumer.java8.listener.listenable.Listenable {
    private EventListener listener;

    @Override
    public void setListener(EventListener eventListener) {
        this.listener = eventListener;
    }

    @Override
    public void triggerListeners(IEventObject eventObject) {
        listener.handleEvent(eventObject);
    }
}
