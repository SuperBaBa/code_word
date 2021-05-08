package org.jarvis.designpattern.observe.observer.impl;

import org.jarvis.designpattern.observe.observer.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author:tennyson  date:2020/8/1
 */
public class ConcretedObserver implements Observer {
    private Logger log = LoggerFactory.getLogger(ConcretedObserver.class);
    private String name;

    public ConcretedObserver(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void immediatelyUpdate(String message) {
        log.info("Tell {} to start {}",name,message);
    }
}
