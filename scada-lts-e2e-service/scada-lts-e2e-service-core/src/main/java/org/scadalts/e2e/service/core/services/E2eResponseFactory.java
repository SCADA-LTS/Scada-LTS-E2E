package org.scadalts.e2e.service.core.services;

import lombok.extern.log4j.Log4j2;
import org.apache.cxf.jaxrs.utils.HttpUtils;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import static org.apache.cxf.jaxrs.utils.HttpUtils.isPayloadEmpty;
import static org.scadalts.e2e.service.core.sessions.SessionUtil.getSessionIdFrom;

@Log4j2
public abstract class E2eResponseFactory {

    public static <T> E2eResponse<T> newResponse(Response response, Class<T> resClass) {
        MediaType mediaType = _getMediaType(response);
        return E2eResponse.<T>builder()
                .headers(new LinkedHashMap<>(response.getHeaders()))
                .value(_getValue(response, resClass))
                .sessionId(getSessionIdFrom(response).orElse(""))
                .status(response.getStatus())
                .mediaType(mediaType.toString())
                .build();
    }

    public static <T> E2eResponse<T> newResponse(Response response, T value) {
        MediaType mediaType = _getMediaType(response);
        return E2eResponse.<T>builder()
                .headers(new LinkedHashMap<>(response.getHeaders()))
                .value(_unformat(value))
                .sessionId(getSessionIdFrom(response).orElse(""))
                .status(response.getStatus())
                .mediaType(mediaType.toString())
                .build();
    }

    public static <T> E2eResponse<T> newResponseForJsonArrayFirst(Response response, GenericType<List<T>> genericType) {
        MediaType mediaType = _getMediaType(response);
        List<T> values = _getParams(response, genericType);
        return E2eResponse.<T>builder()
                .headers(new LinkedHashMap<>(response.getHeaders()))
                .value(_unformat(_calculateValueOne(values)))
                .sessionId(getSessionIdFrom(response).orElse(""))
                .status(response.getStatus())
                .mediaType(mediaType.toString())
                .build();
    }

    public static <T> E2eResponse<List<T>> newResponseForJsonArray(Response response, GenericType<List<T>> genericType) {
        MediaType mediaType = _getMediaType(response);
        List<T> values = _getParams(response, genericType);
        return E2eResponse.<List<T>>builder()
                .headers(new LinkedHashMap<>(response.getHeaders()))
                .value(_unformat(values))
                .sessionId(getSessionIdFrom(response).orElse(""))
                .status(response.getStatus())
                .mediaType(mediaType.toString())
                .build();
    }

    private static <T> List<T> _getParams(Response response, GenericType<List<T>> genericType) {
        return HttpUtils.isPayloadEmpty(response.getStringHeaders()) ? Collections.emptyList()
                : response.readEntity(genericType);
    }

    private static <T> T _calculateValueOne(List<T> list) {
        if(list.isEmpty())
            return null;
        return list.get(0);
    }

    private static <T> List<T> _unformat(List<T> values) {
        List<T> result = new ArrayList<>();
        for (T t: values)
            result.add(_unformat(t));
        return result;
    }

    private static <T> T _unformat(T value) {
        if(value instanceof ValueUnfromatted) {
            @SuppressWarnings("unchecked")
            ValueUnfromatted<T> valueUnformatted = (ValueUnfromatted<T>) value;
            return valueUnformatted.perform();
        }
        return value;
    }

    private static MediaType _getMediaType(Response response) {
        return response.getMediaType() != null ? response.getMediaType() : MediaType.TEXT_HTML_TYPE;
    }


    private static <T> T _getValue(Response response, Class<T> resClass) {
        MediaType mediaType = _getMediaType(response);
        return _unformat(isPayloadEmpty(response.getStringHeaders()) ? null
                : MediaType.APPLICATION_JSON_TYPE.isCompatible(mediaType) ? response.readEntity(resClass)
                : _newInstance(resClass));
    }

    private static <T> T _newInstance(Class<T> resClass) {
        try {
            return resClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
