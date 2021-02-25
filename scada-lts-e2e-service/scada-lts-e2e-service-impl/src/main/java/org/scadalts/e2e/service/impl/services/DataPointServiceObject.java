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
import org.scadalts.e2e.service.impl.services.datapoint.DataPointPropertiesResponse;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.Optional;
import java.util.function.Predicate;

import static org.scadalts.e2e.service.core.utils.ServiceStabilityUtil.applyWhile;
import static org.scadalts.e2e.service.core.utils.ServiceStabilityUtil.applyWhilePredicate;

@Log4j2
@Builder(access = AccessLevel.PACKAGE)
public class DataPointServiceObject implements WebServiceObject {

    public final URL baseUrl;
    private final Client client;

    public Optional<E2eResponse<DataPointPropertiesResponse>> getConfigurationByXid(PointValueParams pointValueParams, long timeout) {
        try {
            E2eResponse<DataPointPropertiesResponse> response = applyWhile(this::_getConfigurationByXid, pointValueParams, new StabilityUtil.Timeout(timeout));
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<E2eResponse<DataPointPropertiesResponse>> getConfigurationByXid(PointValueParams pointValueParams, Predicate<DataPointPropertiesResponse> expectedValue, long timeout) {
        try {
            E2eResponse<DataPointPropertiesResponse> response = applyWhilePredicate(this::_getConfigurationByXid, pointValueParams, new StabilityUtil.Timeout(timeout), expectedValue);
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    private E2eResponse<DataPointPropertiesResponse> _getConfigurationByXid(PointValueParams pointValueParams) {
        String endpoint = baseUrl + "/api/datapoint/getConfigurationByXid/";
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("params: {}", pointValueParams);
        logger.info("endpoint: {}", endpoint);
        logger.info("cookie: {}", cookie);
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        Response response = client
                .target(endpoint)
                .path(pointValueParams.getXid())
                .request(mediaType)
                .cookie(cookie)
                .get();
        return E2eResponseFactory.newResponse(response, DataPointPropertiesResponse.class);
    }

    @Override
    public void close() {
        client.close();
    }
}
