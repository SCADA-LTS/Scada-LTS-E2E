package org.scadalts.e2e.webservice.core.services;

import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Log4j2
public class WebServiceObjectExecutor {

    public static <T> Optional<E2eResponse<T>> execute(Supplier<E2eResponse<T>> supplier) {
        try {
            return Optional.ofNullable(supplier.get());
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            return Optional.empty();
        }
    }

    public static <N, M> Optional<E2eResponse<N>> execute(Function<M, E2eResponse<N>> supplier, M arg) {
        try {
            return Optional.ofNullable(supplier.apply(arg));
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            return Optional.empty();
        }
    }

    public static <T> Optional<E2eResponse<List<T>>> executeWithList(Function<T, E2eResponse<List<T>>> supplier, T arg) {
        try {
            return Optional.ofNullable(supplier.apply(arg));
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            return Optional.empty();
        }
    }
}
