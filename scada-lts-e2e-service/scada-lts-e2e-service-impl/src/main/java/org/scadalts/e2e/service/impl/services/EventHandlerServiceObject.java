package org.scadalts.e2e.service.impl.services;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.apache.cxf.jaxrs.utils.HttpUtils;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.common.utils.StabilityUtil;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.core.services.E2eResponseFactory;
import org.scadalts.e2e.service.core.services.WebServiceObject;
import org.scadalts.e2e.service.core.sessions.CookieFactory;
import org.scadalts.e2e.service.impl.services.eventDetector.EventDetectorParams;
import org.scadalts.e2e.service.impl.services.eventDetector.EventDetectorResponse;
import org.scadalts.e2e.service.impl.services.eventHandler.EventHandlerResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.scadalts.e2e.service.core.utils.ServiceStabilityUtil.applyWhile;
import static org.scadalts.e2e.service.core.utils.ServiceStabilityUtil.executeWhile;

@Log4j2
@Builder(access = AccessLevel.PACKAGE)
public class EventHandlerServiceObject implements WebServiceObject {

    public final URL baseUrl;
    private final Client client;

    public Optional<E2eResponse<List<EventHandlerResponse>>> getAllEventHandlers(long timeout) {
        try {
            E2eResponse<List<EventHandlerResponse>> response = _getAllEventHandlers();
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    private E2eResponse<List<EventHandlerResponse>> _getAllEventHandlers() {
        String endpoint = baseUrl + "/api/eventHandler/getAll";
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("endpoint: {}", endpoint);
        logger.info("cookie: {}", cookie);
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        Response response = client
                .target(endpoint)
                .request(mediaType)
                .cookie(cookie)
                .get();
        List<EventHandlerResponse> list = _getList(response);
        return E2eResponseFactory.newResponse(response, list);
    }

    private List<EventHandlerResponse> _getList(Response response) {
        return HttpUtils.isPayloadEmpty(response.getStringHeaders()) ? Collections.emptyList()
                : response.readEntity(new GenericType<List<EventHandlerResponse>>() {});
    }

    @Override
    public void close() {
        client.close();
    }
}
