package org.jarvis.consumer.java8.observe.subject.impl;

import org.jarvis.consumer.java8.observe.observer.Observer;
import org.jarvis.consumer.java8.observe.subject.Subject;

import java.util.HashSet;
import java.util.Set;

/**
 * author:tennyson  date:2020/8/1
 */
public class ConcretedSubject implements Subject {
    private Set<Observer> observers = new HashSet<>();

    @Override
    public void attachObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllOberserver(String message) {
        observers.forEach(observer -> {
            observer.immediatelyUpdate(message);
        });
    }
}
