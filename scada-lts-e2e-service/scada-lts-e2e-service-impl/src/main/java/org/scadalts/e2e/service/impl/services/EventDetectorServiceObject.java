package org.scadalts.e2e.service.impl.services;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.config.E2eConfiguration;
import org.scadalts.e2e.common.core.utils.StabilityUtil;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.core.services.E2eResponseFactory;
import org.scadalts.e2e.service.core.services.WebServiceObject;
import org.scadalts.e2e.service.core.sessions.CookieFactory;
import org.scadalts.e2e.service.impl.services.eventdetector.EventDetectorParams;
import org.scadalts.e2e.service.impl.services.eventdetector.EventDetectorPostResponse;
import org.scadalts.e2e.service.impl.services.eventdetector.EventDetectorResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.scadalts.e2e.service.core.utils.ServiceStabilityUtil.applyWhile;

@Log4j2
@Builder(access = AccessLevel.PACKAGE)
public class EventDetectorServiceObject implements WebServiceObject {

    public final URL baseUrl;
    private final Client client;

    public Optional<E2eResponse<List<EventDetectorResponse>>> getEventDetectorsByXid(EventDetectorParams eventDetectorParams, long timeout) {
        try {
            E2eResponse<List<EventDetectorResponse>> response = applyWhile(this::_getEventDetectorsByXid, eventDetectorParams, new StabilityUtil.Timeout(timeout));
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<E2eResponse<EventDetectorPostResponse>> setEventDetector(EventDetectorParams eventDetectorParams, long timeout) {
        try {
            E2eResponse<EventDetectorPostResponse> response = applyWhile(this::_setChangeEventDetector, eventDetectorParams, new StabilityUtil.Timeout(timeout));
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public E2eResponse<EventDetectorPostResponse> _setBinaryStateEventDetector(EventDetectorParams eventDetectorParams) {
        String endpoint = baseUrl + "/api/eventDetector/set/";
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("params: {}", eventDetectorParams.getId());
        logger.info("endpoint: {}", endpoint);
        logger.info("cookie: {}", cookie);
        logger.info("body: {}", eventDetectorParams.getBody());
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        Response response = client
                .target(endpoint)
                .path(String.valueOf(eventDetectorParams.getId()))
                .request(mediaType)
                .cookie(cookie)
                .post(Entity.entity(eventDetectorParams.getBody(), MediaType.APPLICATION_JSON));
        return E2eResponseFactory.newResponse(response, EventDetectorPostResponse.class);
    }

    public E2eResponse<EventDetectorPostResponse> _setChangeEventDetector(EventDetectorParams eventDetectorParams) {
        String endpoint = baseUrl + "/api/eventDetector/set/";
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("params: {}", eventDetectorParams.getId());
        logger.info("endpoint: {}", endpoint);
        logger.info("cookie: {}", cookie);
        logger.info("body: {}", eventDetectorParams.getBody());
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        Response response = client
                .target(endpoint)
                .path(String.valueOf(eventDetectorParams.getId()))
                .request(mediaType)
                .cookie(cookie)
                .post(Entity.entity(eventDetectorParams.getBody(), MediaType.APPLICATION_JSON));
        return E2eResponseFactory.newResponse(response, EventDetectorPostResponse.class);
    }

    private E2eResponse<List<EventDetectorResponse>> _getEventDetectorsByXid(EventDetectorParams eventDetectorParams) {
        String endpoint = baseUrl + "/api/eventDetector/getAll/";
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("params: {}", eventDetectorParams);
        logger.info("endpoint: {}", endpoint);
        logger.info("cookie: {}", cookie);
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        Response response = client
                .target(endpoint)
                .path(eventDetectorParams.getXid())
                .request(mediaType)
                .cookie(cookie)
                .get();
        return E2eResponseFactory.newResponseForJsonArray(response, new GenericType<List<EventDetectorResponse>>() {});
    }

    @Override
    public void close() {
        client.close();
    }
}
