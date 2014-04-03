package utils;

import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class PerfLogger {

    // The logger
    private static final Logger LOGGER = LoggerFactory.getLogger("performances");

    private String message;
    private Object[] objs;
    private Long startTime;
    private Long endtime;

    private PerfLogger(String message, Object... objs) {
        this.message = message;
        this.objs = objs;
    }

    private PerfLogger(String message) {
        this.message = message;
    }

    public static PerfLogger start() {
        return new PerfLogger("").resetTime();
    }

    public static PerfLogger start(String message) {
        return new PerfLogger(message).resetTime();
    }

    public static PerfLogger start(String message, Object... objs) {
        return new PerfLogger(message, objs).resetTime();
    }

    private PerfLogger resetTime() {
        startTime = System.currentTimeMillis();
        return this;
    }

    // Follow the Perf4j way of storing perfs
    public void stop() {
        this.endtime = System.currentTimeMillis();
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Start[{}] time[{}] hr[{}] tag[" + this.message + "]",
                    concat(new Object[] { this.startTime, time(), hr() }, this.objs));
        }
    }

    // Follow the Perf4j way of storing perfs
    public void stop(String message, Object... objs) {
        this.endtime = System.currentTimeMillis();
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Start[{}] time[{}] hr[{}] tag[" + message + "]",
                    concat(new Object[] { this.startTime, time(), hr() }, objs));
        }
    }

    public long time() {
        return endtime - startTime;
    }

    private String hr() {
        String result = "";
        long responseTime = time();
        if (responseTime / 60000 > 0) {
            result += responseTime / 60000 + "m, ";
            responseTime = responseTime % 60000;
        }
        if (responseTime / 1000 > 0) {
            result += responseTime / 1000 + "s, ";
            responseTime = responseTime % 1000;
        }
        result += responseTime + "ms";
        return result;
    }

    private static <T> T[] concat(T[] first, T[] second) {
        assert first != null;
        if (second == null) {
            return first;
        } 
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        // Assume SLF4J is bound to logback in the current environment
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        // Print logback's internal status
        StatusPrinter.print(lc);

        for (int i = 0; i < 10; i++) {
            PerfLogger logger = PerfLogger.start();

            Thread.sleep(100);

            logger.stop();
        }

        for (int i = 0; i < 10; i++) {
            PerfLogger logger = PerfLogger.start("Test method #{} in thread {} !", i, "Main");

            Thread.sleep(100);

            logger.stop();
        }

        for (int i = 0; i < 10; i++) {
            PerfLogger logger = PerfLogger.start();

            Thread.sleep(100);

            logger.stop("Test method!");
            logger.stop("Test method #{}", i);
            logger.stop("Test method #{} at {}", i, new Date());
            logger.stop("Test method #{} at {} in thread {} !", i, new Date(), "Main");
        }
    }
}
