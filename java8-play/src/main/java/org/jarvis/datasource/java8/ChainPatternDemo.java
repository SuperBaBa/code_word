package org.jarvis.consumer.java8;

public class ChainPatternDemo {
    private static AbstractLogger getChainOfLoggers() {

        AbstractLogger errorLogger = new org.jarvis.consumer.java8.ErrorLogger(org.jarvis.consumer.java8.AbstractLogger.ERROR);
        AbstractLogger trackLogger = new org.jarvis.consumer.java8.TrackLogger(org.jarvis.consumer.java8.AbstractLogger.TRACK);
        AbstractLogger infoLogger = new org.jarvis.consumer.java8.InfoLogger(org.jarvis.consumer.java8.AbstractLogger.INFO);

        errorLogger.setNextLogger(infoLogger);
        infoLogger.setNextLogger(trackLogger);

        return errorLogger;
    }

    public static void main(String[] args) {
        //责任链调用
        AbstractLogger loggerChain = getChainOfLoggers();

        loggerChain.logMessage(org.jarvis.consumer.java8.AbstractLogger.INFO, "This is an information.第一次调用，只命中INFO");

        loggerChain.logMessage(org.jarvis.consumer.java8.AbstractLogger.TRACK, "This is a debug level information.第二次调用，需要命中Tack");

        loggerChain.logMessage(org.jarvis.consumer.java8.AbstractLogger.ERROR, "This is an error information.第三次调用，需要命中Error");
    }
}
