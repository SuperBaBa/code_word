package org.jarvis.consumer.java8.observe.subject;

import org.jarvis.consumer.java8.observe.observer.Observer;

public interface Subject {
    void attachObserver(Observer observer);

    void detachObserver(Observer observer);

    void notifyAllOberserver(String message);
}
