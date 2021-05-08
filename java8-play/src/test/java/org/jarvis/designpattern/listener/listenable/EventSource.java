package org.jarvis.designpattern.listener.listenable;

import org.jarvis.designpattern.listener.event.IEventObject;
import org.jarvis.designpattern.listener.listener.EventListener;

/**
 * <一句话概述功能>
 *
 * @author Marcus
 * @date 2021/2/23-12:44
 * @description this is function description
 */
public class EventSource implements Listenable {
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
