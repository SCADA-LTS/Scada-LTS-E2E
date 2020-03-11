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

import static org.scadalts.e2e.service.core.utils.ServiceStabilityUtil.applyWhile;

@Log4j2
@Builder(access = AccessLevel.PACKAGE)
public class CmpServiceObject implements WebServiceObject {

    public final URL baseUrl;
    private final Client client;

    public Optional<E2eResponse<CmpParams>> set(CmpParams cmpParams, long timeout) {
        try {
            E2eResponse<CmpParams> response = applyWhile(this::_set, cmpParams, new StabilityUtil.Timeout(timeout));
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    private E2eResponse<CmpParams> _set(CmpParams cmpParams) {
        String endpoint = baseUrl + "/api/cmp/set/";
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("params: {}", cmpParams);
        logger.info("endpoint: {}", endpoint);
        logger.info("cookie: {}", cookie);
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        Response response = client
                .target(endpoint)
                .path(cmpParams.getXid())
                .path(String.valueOf(cmpParams.getValue()))
                .request(mediaType)
                .cookie(cookie)
                .post(Entity.entity(new CmpParams[]{cmpParams}, mediaType));
        List<CmpParams> para = _getCmpParams(response);
        return E2eResponseFactory.newResponseForJsonArrayFirst(response, para);
    }

    private List<CmpParams> _getCmpParams(Response response) {
        return HttpUtils.isPayloadEmpty(response.getStringHeaders()) ? Collections.emptyList()
                : response.readEntity(new GenericType<List<CmpParams>>() {});
    }

    @Override
    public void close() {
        client.close();
    }

}
