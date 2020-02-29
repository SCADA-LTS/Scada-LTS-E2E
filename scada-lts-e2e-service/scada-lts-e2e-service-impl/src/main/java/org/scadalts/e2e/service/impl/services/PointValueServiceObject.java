package org.scadalts.e2e.service.impl.services;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.core.services.E2eResponseFactory;
import org.scadalts.e2e.service.core.services.WebServiceObject;
import org.scadalts.e2e.service.core.sessions.CookieFactory;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.Optional;

import static org.scadalts.e2e.service.core.utils.ServiceStabilityUtil.applyWhile;

@Log4j2
@Builder(access = AccessLevel.PACKAGE)
public class PointValueServiceObject implements WebServiceObject {

    private final URL baseUrl;
    private final Client client;

    public Optional<E2eResponse<PointValueResponse>> getValue(PointValueParams pointValueParams, long timeout) {
        try {
            E2eResponse<PointValueResponse> response = applyWhile(this::_getValue, pointValueParams, timeout);
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<E2eResponse<PointValueResponse>> getValue(PointValueParams pointValueParams, long timeout, Object valueExpected) {
        try {
            E2eResponse<PointValueResponse> response = applyWhile(this::_getValue, pointValueParams, timeout, valueExpected);
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    private E2eResponse<PointValueResponse> _getValue(PointValueParams pointValueParams) {
        String endpoint = baseUrl + "/api/point_value/getValue/";
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        String xid = pointValueParams.getXid();
        logger.info("dataPointXid: {}", xid);
        logger.info("endpoint: {}", endpoint);
        logger.info("cookie: {}", cookie);
        Response response = client.target(endpoint)
                .path(xid)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .cookie(cookie)
                .get();
        return E2eResponseFactory.newResponse(response, PointValueResponse.class);
    }

    @Override
    public void close() {
        client.close();
    }
}
