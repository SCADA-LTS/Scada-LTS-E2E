package org.scadalts.e2e.webservice.impl.services;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.webservice.core.services.E2eResponse;

import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.List;

import static org.scadalts.e2e.webservice.core.session.SessionUtil.getSessionIdFrom;

@Log4j2
abstract class E2eResponseFactory {

    static <T> E2eResponse<T> newResponse(Response response, Class<T> resClass) {
        T value = response.readEntity(resClass);
        return E2eResponse.<T>builder()
                .headers(new LinkedHashMap<>(response.getHeaders()))
                .value(value)
                .sessionId(getSessionIdFrom(response).orElse(""))
                .status(response.getStatus())
                .mediaType(_getMediaType(response))
                .build();
    }

    static <T> E2eResponse<T> newResponseFor(Response response, T value) {
        return E2eResponse.<T>builder()
                .headers(new LinkedHashMap<>(response.getHeaders()))
                .value(value)
                .sessionId(getSessionIdFrom(response).orElse(""))
                .status(response.getStatus())
                .mediaType(_getMediaType(response))
                .build();
    }

    static <T> E2eResponse<T> newResponseForJsonArrayFirst(Response response, List<T> value) {
        return E2eResponse.<T>builder()
                .headers(new LinkedHashMap<>(response.getHeaders()))
                .value(calculateValueOne(value))
                .sessionId(getSessionIdFrom(response).orElse(""))
                .status(response.getStatus())
                .mediaType(_getMediaType(response))
                .build();
    }

    private static <T> T calculateValueOne(List<T> list) {
        return list.stream().findFirst().orElseThrow(IllegalStateException::new);
    }

    private static String _getMediaType(Response response) {
        return response.getMediaType() != null ? response.getMediaType().toString() : "";
    }

}
