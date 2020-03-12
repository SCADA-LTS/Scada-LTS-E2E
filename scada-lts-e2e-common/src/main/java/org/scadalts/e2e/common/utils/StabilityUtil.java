package org.scadalts.e2e.common.utils;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Log4j2
public class StabilityUtil {

    private final static long INTERVAL_MS = 3000;
    private final static int LIMIT = 100;

    public static <T> T waitWhile(Predicate<T> predicate, T arg, Timeout timeout) {
        long time = System.currentTimeMillis();
        int i = 0;
        while(predicate.test(arg)
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            _sleep();
        }
        return arg;
    }

    public static <T> T executeWhile(Predicate<T> predicate, T arg,
                                     FunctionlInterfaces.Executable fun,
                                     Timeout timeout) {
        long time = System.currentTimeMillis();
        int i = 0;
        fun.execute();
        while(predicate.test(arg)
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            _sleep();
            fun.execute();
        }
        return arg;
    }

    public static <T> T executeWhile(Predicate<T> predicate, Supplier<T> fun,
                                     Timeout timeout) {
        long time = System.currentTimeMillis();
        int i = 0;
        T result = fun.get();
        while(predicate.test(result)
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            _sleep();
            result = fun.get();
        }
        return result;
    }

    public static <T, R> R applyWhile(Predicate<R> predicate, Function<T, R> fun, T arg,
                                     Timeout timeout) {
        long time = System.currentTimeMillis();
        int i = 0;
        R result = fun.apply(arg);
        while(predicate.test(result)
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            _sleep();
            result = fun.apply(arg);
        }
        return result;
    }

    public static <T, R, S> R applyWhile(Predicate<S> predicate, S predicateArg, Function<T, R> fun, T arg,
                                      Timeout timeout) {
        long time = System.currentTimeMillis();
        int i = 0;
        R result = fun.apply(arg);
        while(predicate.test(predicateArg)
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            _sleep();
            result = fun.apply(arg);
        }
        return result;
    }

    public static <T, R, S> R applyWhileBi(BiPredicate<R, S> predicate, Function<T, R> fun, T arg,
                                        S expectedValue, Timeout timeout) {
        long time = System.currentTimeMillis();
        int i = 0;
        R result = fun.apply(arg);
        while(predicate.test(result, expectedValue)
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}, value expected: {}", i, expectedValue);
            _sleep();
            result = fun.apply(arg);
        }
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

}
