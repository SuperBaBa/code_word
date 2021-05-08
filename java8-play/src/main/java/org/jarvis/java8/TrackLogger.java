package org.jarvis.java8;

public class TrackLogger extends AbstractLogger {
    @Override
    void write(String message) {
        System.out.println("Tack::Logger"+message);
    }

    public TrackLogger(int level) {
        this.level=level;
    }


}
