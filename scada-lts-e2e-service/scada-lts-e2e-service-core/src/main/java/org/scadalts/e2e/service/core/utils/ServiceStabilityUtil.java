package org.scadalts.e2e.service.core.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.utils.StabilityUtil;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.core.services.GetFormattedValueResponse;
import org.scadalts.e2e.service.core.services.GetValueResponse;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.scadalts.e2e.common.core.utils.FormatUtil.unformat;
import static org.scadalts.e2e.common.core.utils.StabilityUtil.Timeout;

@Log4j2
public class ServiceStabilityUtil {

    public static <T, R> E2eResponse<R> applyWhile(Function<T, E2eResponse<R>> function,
                                                   T arg, Timeout timeout) {

        return StabilityUtil.applyWhile(ServiceStabilityUtil::_is, function, arg, timeout);
    }

    public static <T, R> E2eResponse<R> applyWhileOrThrowException(Function<T, E2eResponse<R>> function,
                                                   T arg, Timeout timeout) {

        return StabilityUtil.applyWhileOrThrowException(ServiceStabilityUtil::_is, function, arg, timeout);
    }

    public static <T, R> E2eResponse<R> applyWhile(Function<T, E2eResponse<R>> function,
                                                   T arg, Timeout timeout, Predicate<E2eResponse<R>> is) {

        return StabilityUtil.applyWhile(is.negate(), function, arg, timeout);
    }

    public static <T, S, R> E2eResponse<R> applyWhile(BiFunction<T, S, E2eResponse<R>> function,
                                                   T arg1, S arg2, Timeout timeout, Predicate<E2eResponse<R>> is) {

        return StabilityUtil.applyWhile(is.negate(), function, arg1, arg2, timeout);
    }

    public static <T, R> E2eResponse<R> applyWhileOrThrowException(Function<T, E2eResponse<R>> function,
                                                                   T arg, Timeout timeout, Predicate<E2eResponse<R>> is) {

        return StabilityUtil.applyWhileOrThrowException(is.negate(), function, arg, timeout);
    }

    public static <T, R extends GetValueResponse
            & GetFormattedValueResponse> E2eResponse<R> applyWhile(Function<T, E2eResponse<R>> function,
                                                                   T arg, Timeout timeout, Object expectedValue) {
        return StabilityUtil.applyWhile(ServiceStabilityUtil::_is, function, arg, expectedValue, timeout);
    }

    public static <T, R> E2eResponse<R> applyWhilePredicate(Function<T, E2eResponse<R>> function,
                                                                   T arg, Timeout timeout, Predicate<R> predicate) {
        return StabilityUtil.applyWhile(ServiceStabilityUtil::_isPredicate, function, arg, predicate, timeout);
    }

    public static <R> E2eResponse<R> executeWhile(Supplier<E2eResponse<R>> function, Timeout timeout) {
        return StabilityUtil.executeWhile(ServiceStabilityUtil::_is, function, timeout);
    }

    public static <R> E2eResponse<R> executeWhileOrThrowException(Supplier<E2eResponse<R>> function, Timeout timeout) {
        return StabilityUtil.executeWhileOrThrowException(ServiceStabilityUtil::_is, function, timeout);
    }


    private static <R> boolean _is(E2eResponse<R> response) {
        return !_isStatusUnauth(response)
                && !_isStatusOk(response);
    }

    private static <R extends GetValueResponse
            & GetFormattedValueResponse> boolean _is(E2eResponse<R> response, Object valueExpected) {
        return !_isStatusUnauth(response)
                && !(_isStatusOk(response)
                && _isValueExpected(response, valueExpected));
    }

    private static <R> boolean _isPredicate(E2eResponse<R> response, Predicate<R> valueExpected) {
        return !_isStatusUnauth(response)
                && !(_isStatusOk(response)
                && valueExpected.test(response.getValue()));
    }

    private static <R> boolean _isStatusOk(E2eResponse<R> response) {
        return response != null && (response.getStatus() == 200 || response.getStatus() == 302);
    }

    private static <R> boolean _isStatusUnauth(E2eResponse<R> response) {
        return response != null && response.getStatus() == 401;
    }

    private static <R extends GetValueResponse
            & GetFormattedValueResponse> boolean _isValueExpected(E2eResponse<R> response,
                                                                  Object expectedValue) {
        if(isNull(response))
            return false;
        R result = response.getValue();
        if(isNull(result))
            return false;
        return _isValueExpected(result, expectedValue) || _isFormattedValueExpected(result, expectedValue);
    }

    private static <R extends GetValueResponse
            & GetFormattedValueResponse> boolean _isFormattedValueExpected(R result, Object expectedValue) {
        return isNotBlank(result.getFormattedValue())
                && Objects.equals(unformat(result.getFormattedValue()), unformat(expectedValue));
    }

    private static <R extends GetValueResponse> boolean _isValueExpected(R result, Object expectedValue) {
        return isNotBlank(result.getValue())
                && Objects.equals(unformat(result.getValue()),  unformat(expectedValue));
    }
}