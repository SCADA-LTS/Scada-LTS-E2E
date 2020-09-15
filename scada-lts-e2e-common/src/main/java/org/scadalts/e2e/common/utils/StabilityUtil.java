package org.scadalts.e2e.common.utils;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.function.*;

@Log4j2
public class StabilityUtil {

    private final static long INTERVAL_MS = 3000;
    private final static int LIMIT = 100;

    public static <T> T waitWhile(Predicate<T> whileDo, T arg, Timeout timeout) {
        long time = System.currentTimeMillis();
        int i = 0;
        while(whileDo.test(arg)
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            _sleep();
        }
        if(whileDo.test(arg))
            logger.warn("Result does not meet the condition!");
        return arg;
    }

    public static void waitWhile(BooleanSupplier whileDo, Timeout timeout) {
        long time = System.currentTimeMillis();
        int i = 0;
        while(whileDo.getAsBoolean()
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            _sleep();
        }
        if(whileDo.getAsBoolean())
            logger.warn("Result does not meet the condition!");
    }

    public static <T> T executeWhile(Predicate<T> whileDo, T arg,
                                     FunctionlInterfaces.Executable executor,
                                     Timeout timeout) {
        long time = System.currentTimeMillis();
        int i = 0;
        _execute(executor);
        while(whileDo.test(arg)
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            _sleep();
            _execute(executor);
        }
        if(whileDo.test(arg))
            logger.warn("Result does not meet the condition!");
        return arg;
    }

    public static <T> T executeWhile(Predicate<T> whileDo, Supplier<T> supplier,
                                     Timeout timeout) {
        long time = System.currentTimeMillis();
        int i = 0;

        T result = _execute(supplier);
        while((result == null
                || whileDo.test(result))
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            _sleep();
            result = _execute(supplier);
        }
        if(result == null || whileDo.test(result))
            logger.warn("Result does not meet the condition! Result: {}", result);
        return result;
    }

    public static <T, R> R applyWhile(Predicate<R> whileDo, Function<T, R> function, T arg,
                                     Timeout timeout) {
        long time = System.currentTimeMillis();
        int i = 0;
        R result = _execute(function, arg);
        while((result == null
                || whileDo.test(result))
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            _sleep();
            result = _execute(function, arg);
        }
        if(result == null || whileDo.test(result))
            logger.warn("Result does not meet the condition! Result: {}", result);
        return result;
    }

    public static <T, R, S> R applyWhileBi(BiPredicate<R, S> whileDo, Function<T, R> function, T arg,
                                        S expectedValue, Timeout timeout) {
        long time = System.currentTimeMillis();
        int i = 0;
        R result = _execute(function, arg);
        while((result == null
                || whileDo.test(result, expectedValue))
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}, value expected: {}", i, expectedValue);
            _sleep();
            result = _execute(function, arg);
        }
        if(result == null || whileDo.test(result, expectedValue))
            logger.warn("Result does not meet the condition! Result: {}, Expected value: {}", result, expectedValue);
        return result;
    }

    @Data
    public static class Timeout {
        private final long value;
    }

    private static void _sleep() {
        try {
            Thread.sleep(INTERVAL_MS);
        } catch (InterruptedException e) {
            logger.warn(e.getMessage(), e);
        }
    }

    private static boolean _isExceededLimit(int i) {
        return i > LIMIT;
    }

    private static boolean _isExceededTimeout(Timeout timeout, long time) {
        return System.currentTimeMillis() - time > timeout.getValue();
    }


    private static void _execute(FunctionlInterfaces.Executable executor) {
        try {
            executor.execute();
        } catch (Exception ex) {
            logger.warn(ex.getMessage(), ex);
        }
    }

    private static <T> T _execute(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception ex) {
            logger.warn(ex.getMessage(), ex);
            return null;
        }
    }

    private static <T, R> R _execute(Function<T, R> function, T arg) {
        try {
            return function.apply(arg);
        } catch (Exception ex) {
            logger.warn(ex.getMessage(), ex);
            return null;
        }
    }
}
