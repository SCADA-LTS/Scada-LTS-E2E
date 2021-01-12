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
import org.scadalts.e2e.service.impl.services.eventHandler.EventHandlerParams;
import org.scadalts.e2e.service.impl.services.eventHandler.EventHandlerResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.text.MessageFormat;
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
            E2eResponse<List<EventHandlerResponse>> response = executeWhile(this::_getAllEventHandlers, new StabilityUtil.Timeout(timeout));
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

    public Optional<E2eResponse<EventHandlerResponse>> getEventHandlerByXid(EventHandlerParams eventHandlerParams, long timeout) {
        try {
            E2eResponse<EventHandlerResponse> response = applyWhile(this::_getEventHandlerByXid, eventHandlerParams, new StabilityUtil.Timeout(timeout));
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    private E2eResponse<EventHandlerResponse> _getEventHandlerByXid(EventHandlerParams eventHandlerParams) {
        String endpoint = baseUrl + "/api/eventHandler/get/";
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("params: {}", eventHandlerParams.getXid());
        logger.info("endpoint: {}", endpoint);
        logger.info("cookie: {}", cookie);
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        Response response = client
                .target(endpoint)
                .path(eventHandlerParams.getXid())
                .request(mediaType)
                .cookie(cookie)
                .get();
        return E2eResponseFactory.newResponse(response, EventHandlerResponse.class);
    }

    private List<EventHandlerResponse> _getList(Response response) {
        return HttpUtils.isPayloadEmpty(response.getStringHeaders()) ? Collections.emptyList()
                : response.readEntity(new GenericType<List<EventHandlerResponse>>() {});
    }

    public Optional<E2eResponse<String>> createEventHandlerTypeEmail(EventHandlerParams eventHandlerParams, long timeout) {
        try {
            E2eResponse<String> response = applyWhile(this::_createEventHandlerTypeEmail, eventHandlerParams, new StabilityUtil.Timeout(timeout));
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    private E2eResponse<String> _createEventHandlerTypeEmail(EventHandlerParams eventHandlerParams) {
        String endpoint = MessageFormat.format("{0}/api/eventHandler/set/{1}/{2}/{3}/2", baseUrl,
                String.valueOf(eventHandlerParams.getTypeId()),
                String.valueOf(eventHandlerParams.getTypeRef1()),
                String.valueOf(eventHandlerParams.getTypeRef2())
                );
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("params: {} {} {}", eventHandlerParams.getTypeId(), eventHandlerParams.getTypeRef1(), eventHandlerParams.getTypeRef2());
        logger.info("endpoint: {}", endpoint);
        logger.info("cookie: {}", cookie);
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        Response response = client
                .target(endpoint)
                .request(mediaType)
                .cookie(cookie)
                .post(Entity.entity(eventHandlerParams.getBody(), MediaType.APPLICATION_JSON));
        return E2eResponseFactory.newResponse(response, String.class);
    }

    @Override
    public void close() {
        client.close();
    }
}
