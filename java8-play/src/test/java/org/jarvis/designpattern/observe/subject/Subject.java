package org.jarvis.designpattern.observe.subject;

import org.jarvis.designpattern.observe.observer.Observer;

public interface Subject {
    void attachObserver(Observer observer);

    void detachObserver(Observer observer);

    void notifyAllOberserver(String message);
}
