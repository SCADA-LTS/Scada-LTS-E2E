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
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.eventDetector.EventDetectorParams;
import org.scadalts.e2e.service.impl.services.eventDetector.EventDetectorPostResponse;
import org.scadalts.e2e.service.impl.services.eventDetector.EventDetectorResponse;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
        List<EventDetectorResponse> list = _getList(response);
        return E2eResponseFactory.newResponse(response, list);
    }

    private List<EventDetectorResponse> _getList(Response response) {
        return HttpUtils.isPayloadEmpty(response.getStringHeaders()) ? Collections.emptyList()
                : response.readEntity(new GenericType<List<EventDetectorResponse>>() {});
    }

    public Optional<E2eResponse<EventDetectorPostResponse>> setEventDetector(EventDetectorParams eventDetectorParams, long timeout) {
        try {
            E2eResponse<EventDetectorPostResponse> response = applyWhile(this::_setBinaryStateEventDetector, eventDetectorParams, new StabilityUtil.Timeout(timeout));
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    private E2eResponse<EventDetectorPostResponse> _setBinaryStateEventDetector(EventDetectorParams eventDetectorParams) {
        String endpoint = baseUrl + "/api/eventDetector/set/binary/state/xid/";
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("params: {}", eventDetectorParams.getXid());
        logger.info("endpoint: {}", endpoint);
        logger.info("cookie: {}", cookie);
        logger.info("body: {}", eventDetectorParams.getBody());
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        Response response = client
                .target(endpoint)
                .path(eventDetectorParams.getXid())
                .request(mediaType)
                .cookie(cookie)
//                .post(Entity.entity(new EventDetectorResponse[]{eventDetectorParams.getBody()}, mediaType));
                .post(Entity.entity(eventDetectorParams.getBody(), MediaType.APPLICATION_JSON));
//        String res = response.readEntity(String.class);
//                .post(null);
        return E2eResponseFactory.newResponse(response, EventDetectorPostResponse.class);
    }

    @Override
    public void close() {
        client.close();
    }
}
