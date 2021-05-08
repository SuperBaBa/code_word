package org.jarvis.java8;

public class ChainPatternDemo {
    private static AbstractLogger getChainOfLoggers() {

        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger trackLogger = new TrackLogger(AbstractLogger.TRACK);
        AbstractLogger infoLogger = new InfoLogger(AbstractLogger.INFO);

        errorLogger.setNextLogger(infoLogger);
        infoLogger.setNextLogger(trackLogger);

        return errorLogger;
    }

    public static void main(String[] args) {
        //责任链调用
        AbstractLogger loggerChain = getChainOfLoggers();

        loggerChain.logMessage(AbstractLogger.INFO, "This is an information.第一次调用，只命中INFO");

        loggerChain.logMessage(AbstractLogger.TRACK, "This is a debug level information.第二次调用，需要命中Tack");

        loggerChain.logMessage(AbstractLogger.ERROR, "This is an error information.第三次调用，需要命中Error");
    }
}
