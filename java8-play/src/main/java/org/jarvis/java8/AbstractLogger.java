package org.jarvis.java8;

public abstract class AbstractLogger {

    public final static int TRACK = 1;
    public final static int INFO = 2;
    public final static int ERROR = 3;

    private AbstractLogger nextLogger;
    public int level;

    public void setNextLogger(AbstractLogger abstractLogger) {
        this.nextLogger = abstractLogger;
    }

    public void logMessage(int level, String message) {
        if (this.level <= level) {
            write(message);
            return;
        }
        if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        }
    }

    abstract void write(String message);
}
