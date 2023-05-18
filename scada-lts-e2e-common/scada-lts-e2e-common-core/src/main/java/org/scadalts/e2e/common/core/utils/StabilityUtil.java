package org.scadalts.e2e.common.core.utils;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.exceptions.E2eTestException;

import java.util.function.*;

@Log4j2
public class StabilityUtil {

    private static final long INTERVAL_MS = 5000;
    private static final int LIMIT = 100;

    public static <T> T waitWhile(Predicate<T> whileDo, T arg, Timeout timeout) {
        return waitWhile(whileDo, arg, timeout, true);
    }

    public static <T> T waitWhile(Predicate<T> whileDo, T arg, Timeout timeout, boolean safe) {
        long time = System.currentTimeMillis();
        int i = 0;
        while(whileDo.test(arg)
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            _sleep();
        }
        if(whileDo.test(arg)) {
            if(safe) {
                logger.warn("Result does not meet the condition!");
                return arg;
            }
            throw new E2eTestException("Result does not meet the condition!");
        }
        return arg;
    }

    public static void waitWhile(BooleanSupplier whileDo, Timeout timeout) {
        waitWhile(whileDo, timeout, true);
    }

    public static void waitWhile(BooleanSupplier whileDo, Timeout timeout, boolean safe) {
        long time = System.currentTimeMillis();
        int i = 0;
        while(whileDo.getAsBoolean()
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            _sleep();
        }
        if(whileDo.getAsBoolean()) {
            if(safe) {
                logger.warn("Result does not meet the condition!");
            }
            throw new E2eTestException("Result does not meet the condition!");
        }
    }

    public static <T> T executeWhile(Predicate<T> whileDo, T arg,
                                     FunctionlInterfaces.Executable executor,
                                     Timeout timeout) {
        return executeWhile(whileDo, arg, executor, timeout, true);
    }

    public static <T> T executeWhile(Predicate<T> whileDo, T arg,
                                     FunctionlInterfaces.Executable executor,
                                     Timeout timeout, boolean safe) {
        long time = System.currentTimeMillis();
        int i = 0;
        _execute(executor);
        while(whileDo.test(arg)
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            _execute(executor);
            _sleep();
        }
        if(whileDo.test(arg)) {
            if(safe) {
                logger.warn("Result does not meet the condition!");
                return arg;
            }
            throw new E2eTestException("Result does not meet the condition!");
        }
        return arg;
    }

    public static <T> T executeWhile(Predicate<T> whileDo, Supplier<T> supplier,
                                     Timeout timeout) {
        return executeWhile(whileDo, supplier, timeout, true);
    }

    public static <T> T executeWhile(Predicate<T> whileDo, Supplier<T> supplier,
                                     Timeout timeout, boolean safe) {
        long time = System.currentTimeMillis();
        int i = 0;

        T result = _execute(supplier);
        while((result == null
                || whileDo.test(result))
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            result = _execute(supplier);
            _sleep();
        }
        if(result == null || whileDo.test(result)) {
            if(safe) {
                logger.warn("Result does not meet the condition! Result: {}", result);
                return result;
            }
            throw new E2eTestException("Result does not meet the condition! Result: " + result);
        }
        return result;
    }

    public static <T> T executeWhileOrThrowException(Predicate<T> whileDo, Supplier<T> supplier,
                                                     Timeout timeout) {
        return executeWhileOrThrowException(whileDo, supplier, timeout, true);
    }

    public static <T> T executeWhileOrThrowException(Predicate<T> whileDo, Supplier<T> supplier,
                                     Timeout timeout, boolean safe) {
        long time = System.currentTimeMillis();
        int i = 0;

        T result = supplier.get();
        while((result == null
                || whileDo.test(result))
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            result = supplier.get();
            _sleep();
        }
        if(result == null || whileDo.test(result)) {
            if(safe) {
                logger.warn("Result does not meet the condition! Result: {}", result);
                return result;
            }
            throw new E2eTestException("Result does not meet the condition! Result: " + result);
        }
        return result;
    }

    public static <T, R> R applyWhile(Predicate<R> whileDo, Function<T, R> function, T arg,
                                      Timeout timeout) {
        return applyWhile(whileDo, function, arg, timeout, true);
    }

    public static <T, S, R> R applyWhile(Predicate<R> whileDo, BiFunction<T, S, R> function, T arg1, S arg2,
                                         Timeout timeout) {
        return applyWhile(whileDo, function, arg1, arg2, timeout, true);
    }

    public static <T, S, R> R applyWhile(Predicate<R> whileDo, BiFunction<T, S, R> function, T arg1, S arg2,
                                      Timeout timeout, boolean safe) {
        long time = System.currentTimeMillis();
        int i = 0;
        R result = _execute(function, arg1, arg2);
        while((result == null
                || whileDo.test(result))
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            result = _execute(function, arg1, arg2);
            _sleep();
        }
        if(result == null || whileDo.test(result)) {
            if(safe) {
                logger.warn("Result does not meet the condition! Result: {}", result);
                return result;
            }
            throw new E2eTestException("Result does not meet the condition! Result: " + result);
        }
        return result;
    }

    public static <T, R> R applyWhile(Predicate<R> whileDo, Function<T, R> function, T arg,
                                     Timeout timeout, boolean safe) {
        long time = System.currentTimeMillis();
        int i = 0;
        R result = _execute(function, arg);
        while((result == null
                || whileDo.test(result))
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            result = _execute(function, arg);
            _sleep();
        }
        if(result == null || whileDo.test(result)) {
            if(safe) {
                logger.warn("Result does not meet the condition! Result: {}", result);
                return result;
            }
            throw new E2eTestException("Result does not meet the condition! Result: " + result);
        }
        return result;
    }

    public static <T, R> R applyWhileOrThrowException(Predicate<R> whileDo, Function<T, R> function, T arg,
                                                      Timeout timeout) {
        return applyWhileOrThrowException(whileDo, function, arg, timeout, false);
    }

    public static <T, R> R applyWhileOrThrowException(Predicate<R> whileDo, Function<T, R> function, T arg,
                                      Timeout timeout, boolean safe) {
        long time = System.currentTimeMillis();
        int i = 0;
        R result = function.apply(arg);
        while((result == null
                || whileDo.test(result))
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}", i);
            result = function.apply(arg);
            _sleep();
        }
        if(result == null || whileDo.test(result)) {
            if(safe) {
                logger.warn("Result does not meet the condition! Result: {}", result);
                return result;
            }
            throw new E2eTestException("Result does not meet the condition! Result: " + result);
        }
        return result;
    }

    public static <T, R, S> R applyWhile(BiPredicate<R, S> whileDo, Function<T, R> function, T arg,
                                         S expectedValue, Timeout timeout) {
        return applyWhile(whileDo, function, arg, expectedValue, timeout, true);
    }

    public static <T, R, S> R applyWhile(BiPredicate<R, S> whileDo, Function<T, R> function, T arg,
                                         S expectedValue, Timeout timeout, boolean safe) {
        long time = System.currentTimeMillis();
        int i = 0;
        R result = _execute(function, arg);
        while((result == null
                || whileDo.test(result, expectedValue))
                && !_isExceededTimeout(timeout, time)
                && !_isExceededLimit(i)) {
            i++;
            logger.info("try: {}, value expected: {}", i, expectedValue);
            result = _execute(function, arg);
            _sleep();
        }
        if(result == null || whileDo.test(result, expectedValue)) {
            if(safe) {
                logger.warn("Result does not meet the condition! Result: {}, Expected value: {}", result, expectedValue);
                return result;
            }
            throw new E2eTestException("Result does not meet the condition! Result: " + result + ", Expected value:" + expectedValue);
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
            Thread.currentThread().interrupt();
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
    private static <T, S, R> R _execute(BiFunction<T, S, R> function, T arg1, S arg2) {
        try {
            return function.apply(arg1, arg2);
        } catch (Exception ex) {
            logger.warn(ex.getMessage(), ex);
            return null;
        }
    }
}
