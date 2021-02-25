package org.scadalts.e2e.common.core.utils;

public class FunctionlInterfaces {
    @FunctionalInterface
    public interface Executable {
        void execute();
    }

    @FunctionalInterface
    public interface TriFunction<T, U, S, R> {
        R apply(T arg1, U arg2, S arg3);
    }

    @FunctionalInterface
    public interface TriConsumer<T, U, S> {
        void accept(T arg1, U arg2, S arg3);
    }

    @FunctionalInterface
    public interface ConsumerThrowable<T> {
        void accept(T arg1) throws Exception;
    }
}
