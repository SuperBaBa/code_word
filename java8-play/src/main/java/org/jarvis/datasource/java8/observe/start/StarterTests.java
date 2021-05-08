package org.jarvis.consumer.java8.observe.start;

import org.jarvis.consumer.java8.observe.observer.Observer;
import org.jarvis.consumer.java8.observe.observer.impl.ConcretedObserver;
import org.jarvis.consumer.java8.observe.subject.Subject;
import org.jarvis.consumer.java8.observe.subject.impl.ConcretedSubject;

/**
 * author:tennyson  date:2020/8/1
 */
public class StarterTests {
    public static void main(String[] args) {
        Observer user1 = new ConcretedObserver("Lovelace");
        Observer user2 = new ConcretedObserver("Jarvis");
        Observer user3 = new ConcretedObserver("Edwin");

        Subject subject = new ConcretedSubject();
        subject.attachObserver(user1);
        subject.attachObserver(user2);
        subject.attachObserver(user3);
        subject.notifyAllOberserver("learning");
    }
}
