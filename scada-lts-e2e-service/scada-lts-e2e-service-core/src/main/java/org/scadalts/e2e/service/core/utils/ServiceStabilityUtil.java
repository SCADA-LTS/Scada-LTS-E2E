package org.scadalts.e2e.service.core.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.utils.StabilityUtil;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.core.services.GetFormattedValueResponse;
import org.scadalts.e2e.service.core.services.GetValueResponse;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.scadalts.e2e.common.utils.FormatUtil.unformat;
import static org.scadalts.e2e.common.utils.StabilityUtil.*;

@Log4j2
public class ServiceStabilityUtil {

    public static <T, R> E2eResponse<R> applyWhile(Function<T, E2eResponse<R>> function, T arg, long timeout) {

        long time = System.currentTimeMillis();
        int i = 0;
        E2eResponse<R> response = function.apply(arg);
        logger.debug("response: {}", response);
        while(_is(timeout, time, i, response)) {
            logger.info("i: {}", ++i);
            sleep();
            response = function.apply(arg);
        }
        return response;
    }

    public static <T, R extends GetValueResponse & GetFormattedValueResponse> E2eResponse<R> applyWhile(Function<T, E2eResponse<R>> function,
                                                                                                        T arg, long timeout, Object expectedValue) {
        long time = System.currentTimeMillis();
        int i = 0;
        E2eResponse<R> response = function.apply(arg);
        logger.debug("response: {}", response);
        while(_is(timeout, expectedValue, time, i, response)) {
            i++;
            logger.info("i: {}", i);
            sleep();
            response = function.apply(arg);
        }
        return response;
    }

    public static <R> E2eResponse<R> applyWhile(Supplier<E2eResponse<R>> function, long timeout) {

        long time = System.currentTimeMillis();
        int i = 0;
        E2eResponse<R> response = function.get();
        logger.debug("response: {}", response);
        while(_is(timeout, time, i, response)) {
            i++;
            logger.info("i: {}", i);
            sleep();
            response = function.get();
        }
        return response;
    }

    private static <R extends GetValueResponse & GetFormattedValueResponse> boolean _is(long timeout, Object valueExpected, long time, int i,
                                                            E2eResponse<R> response) {
        return !_isStatusUnauth(response)
                && !(_isStatusOk(response)
                && _isValueExpected(response, valueExpected))
                && !isExceededTimeout(new StabilityUtil.Timeout(timeout), time)
                && !isExceededLimit(i);
    }

    private static <R> boolean _is(long timeout, long time, int i, E2eResponse<R> response) {
        return !_isStatusUnauth(response)
                && !_isStatusOk(response)
                && !isExceededTimeout(new StabilityUtil.Timeout(timeout), time)
                && !isExceededLimit(i);
    }

    private static <R> boolean _isStatusOk(E2eResponse<R> response) {
        return response != null && (response.getStatus() == 200 || response.getStatus() == 302);
    }

    private static <R> boolean _isStatusUnauth(E2eResponse<R> response) {
        return response != null && response.getStatus() == 401;
    }

    private static <R extends GetValueResponse & GetFormattedValueResponse> boolean _isValueExpected(E2eResponse<R> response, Object expectedValue) {
        if(isNull(response))
            return false;
        R result = response.getValue();
        if(isNull(result))
            return false;
        return _isValueExpected(result, expectedValue) || _isFormattedValueExpected(result, expectedValue);
    }

    private static <R extends GetValueResponse & GetFormattedValueResponse> boolean _isFormattedValueExpected(R result, Object expectedValue) {
        return isNotBlank(result.getFormattedValue())
                && Objects.equals(unformat(result.getFormattedValue()), unformat(expectedValue));
    }

    private static <R extends GetValueResponse> boolean _isValueExpected(R result, Object expectedValue) {
        return isNotBlank(result.getValue()) && Objects.equals(unformat(result.getValue()),  unformat(expectedValue));
    }
}