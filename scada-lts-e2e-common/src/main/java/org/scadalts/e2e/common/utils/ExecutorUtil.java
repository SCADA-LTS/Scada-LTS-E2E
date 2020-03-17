package org.scadalts.e2e.common.utils;

import lombok.extern.log4j.Log4j2;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Log4j2
public class ExecutorUtil {

    public static <T, U, S, R, E extends Exception> R executeTriFunction(FunctionlInterfaces.TriFunction<T, U, S, R> triFunction,
                                                                         T arg1, U arg2, S arg3,
                                                                         Function<Throwable, E> exc) throws E {
        try {
            return triFunction.apply(arg1, arg2, arg3);
        } catch (Throwable ex) {
            throw exc.apply(ex);
        }
    }

    public static <T, U, R, E extends Exception> R executeBiFunction(BiFunction<T, U, R> biFunction, T arg1, U arg2,
                                                                     Function<Throwable, E> exc) throws E {
        try {
            return biFunction.apply(arg1, arg2);
        } catch (Throwable ex) {
            throw exc.apply(ex);
        }
    }

    public static <T, R, E extends Exception> R executeFunction(Function<T, R> function, T arg,
                                                                Function<Throwable, E> exc) throws E {
        try {
            return function.apply(arg);
        } catch (Throwable ex) {
            throw exc.apply(ex);
        }
    }

    public static <R, E extends Exception> R executeSupplier(Supplier<R> supplier, Function<Throwable, E> exc) throws E {
        try {
            return supplier.get();
        } catch (Throwable ex) {
            throw exc.apply(ex);
        }
    }

    public static <T, E extends Exception> void executeConsumer(Consumer<T> consumer, T arg,
                                                                Function<Throwable, E> exc) throws E {
        try {
            consumer.accept(arg);
        } catch (Throwable ex) {
            throw exc.apply(ex);
        }
    }

    public static <E extends Exception> void execute(FunctionlInterfaces.Executable executor,
                                                     Function<Throwable, E> exc) throws E {
        try {
            executor.execute();
        } catch (Throwable ex) {
            throw exc.apply(ex);
        }
    }

    public static <K, R, E extends Exception> void execute(FunctionlInterfaces.Executable executor,
                                                           Function<K, R> executeIfException, K arg,
                                                           Function<Throwable, E> exc) throws E {
        try {
            executor.execute();
        } catch (Throwable ex) {
            try {
                executeIfException.apply(arg);
            } catch (Throwable ex2) {
                logger.error(ex2.getMessage(), ex2);
            }
            throw exc.apply(ex);
        }
    }
}
