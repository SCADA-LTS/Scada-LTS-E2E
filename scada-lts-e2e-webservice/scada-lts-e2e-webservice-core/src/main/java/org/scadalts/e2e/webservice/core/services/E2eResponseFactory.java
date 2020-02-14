package org.scadalts.e2e.webservice.core.services;

import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.List;

import static org.apache.cxf.jaxrs.utils.HttpUtils.isPayloadEmpty;
import static org.scadalts.e2e.webservice.core.sessions.SessionUtil.getSessionIdFrom;

@Log4j2
public abstract class E2eResponseFactory {

    public static <T> E2eResponse<T> newResponse(Response response, Class<T> resClass) {
        return E2eResponse.<T>builder()
                .headers(new LinkedHashMap<>(response.getHeaders()))
                .value(isPayloadEmpty(response.getStringHeaders()) ? null
                        : response.readEntity(resClass))
                .sessionId(getSessionIdFrom(response).orElse(""))
                .status(response.getStatus())
                .mediaType(_getMediaType(response))
                .build();
    }

    public static <T> E2eResponse<T> newResponse(Response response, T value) {
        return E2eResponse.<T>builder()
                .headers(new LinkedHashMap<>(response.getHeaders()))
                .value(value)
                .sessionId(getSessionIdFrom(response).orElse(""))
                .status(response.getStatus())
                .mediaType(_getMediaType(response))
                .build();
    }

    public static <T> E2eResponse<T> newResponseForJsonArrayFirst(Response response, List<T> value) {
        return E2eResponse.<T>builder()
                .headers(new LinkedHashMap<>(response.getHeaders()))
                .value(calculateValueOne(value))
                .sessionId(getSessionIdFrom(response).orElse(""))
                .status(response.getStatus())
                .mediaType(_getMediaType(response))
                .build();
    }

    private static <T> T calculateValueOne(List<T> list) {
        if(list.isEmpty())
            return null;
        return list.get(0);
    }

    private static String _getMediaType(Response response) {
        return response.getMediaType() != null ? response.getMediaType().toString() : "";
    }

}
