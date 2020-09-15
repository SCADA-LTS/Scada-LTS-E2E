package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.common.utils.StabilityUtil;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class TestStabilityUtil {

    public static <T, U, R> R executeWhilePredicate(Predicate<R> predicate, BiFunction<T, U, R> fun, T arg1, U arg2) {
        return StabilityUtil.executeWhile(predicate, () -> fun.apply(arg1, arg2), new StabilityUtil.Timeout(TestImplConfiguration.waitingAfterSetPointValueMs));
    }

    public static <T, R> R executeWhilePredicate(Predicate<R> predicate, Function<T, R> fun, T arg1) {
        return StabilityUtil.executeWhile(predicate, () -> fun.apply(arg1), new StabilityUtil.Timeout(TestImplConfiguration.waitingAfterSetPointValueMs));
    }
}
