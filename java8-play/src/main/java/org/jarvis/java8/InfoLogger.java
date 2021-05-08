package org.jarvis.java8;

public class InfoLogger extends AbstractLogger {
    @Override
    void write(String message) {
        System.out.println("Info::Logger"+message);
    }

    public InfoLogger(int level) {
        this.level = level;
    }
}
