package org.scadalts.e2e.service.core.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.core.services.get.GetConfig;
import org.scadalts.e2e.service.core.services.get.GetServiceObject;
import org.scadalts.e2e.service.core.services.get.UniversalServiceObjectFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
public final class UniversalServiceUtil {

    private UniversalServiceUtil() {}

    public static E2eResponse<String> getAsString(GetConfig getConfig, long timeout) {
        try {
            return _getString(getConfig, timeout);
        } catch (Throwable th) {
            logger.warn(th.getMessage(), th);
            return E2eResponse.empty();
        }
    }

    public static <T> E2eResponse<Map<String, T>> getAsMap(GetConfig getConfig, long timeout, Class<T> token) {
        try {
            return _getMap(getConfig, timeout, token);
        } catch (Throwable th) {
            logger.warn(th.getMessage(), th);
            return E2eResponse.empty();
        }
    }

    public static <T> E2eResponse<List<T>> getAsList(GetConfig getConfig, long timeout, Class<T> token) {
        try {
            return _getList(getConfig, timeout, token);
        } catch (Throwable th) {
            logger.warn(th.getMessage(), th);
            return E2eResponse.empty();
        }
    }

    public static <T> E2eResponse<T> get(GetConfig getConfig, long timeout, Class<T> token) {
        try {
            return _get(getConfig, timeout, token);
        } catch (Throwable th) {
            logger.warn(th.getMessage(), th);
            return E2eResponse.empty();
        }
    }

    private static <T> E2eResponse<Map<String, T>> _getMap(GetConfig getConfig, long timeout, Class<T> token) {
        try (GetServiceObject getServiceObject =
                     UniversalServiceObjectFactory.newGetServiceObject()) {
            Optional<E2eResponse<Map<String, T>>> responseOpt = getServiceObject.getAsMap(getConfig, timeout, token);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    private static <T> E2eResponse<List<T>> _getList(GetConfig getConfig, long timeout, Class<T> token) {
        try (GetServiceObject getServiceObject =
                     UniversalServiceObjectFactory.newGetServiceObject()) {
            Optional<E2eResponse<List<T>>> responseOpt = getServiceObject.getAsList(getConfig, timeout, token);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    private static <T> E2eResponse<T> _get(GetConfig getConfig, long timeout, Class<T> token) {
        try (GetServiceObject getServiceObject =
                     UniversalServiceObjectFactory.newGetServiceObject()) {
            Optional<E2eResponse<T>> responseOpt = getServiceObject.get(getConfig, timeout, token);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    private static E2eResponse<String> _getString(GetConfig getConfig, long timeout) {
        try (GetServiceObject getServiceObject =
                     UniversalServiceObjectFactory.newGetServiceObject()) {
            Optional<E2eResponse<String>> responseOpt = getServiceObject.getAsString(getConfig, timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }
}
