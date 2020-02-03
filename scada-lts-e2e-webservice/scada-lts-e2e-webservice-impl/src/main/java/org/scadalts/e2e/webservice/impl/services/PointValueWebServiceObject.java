package org.scadalts.e2e.webservice.impl.services;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.webservice.core.config.WebServiceObjectConfiguration;
import org.scadalts.e2e.webservice.core.services.E2eResponse;
import org.scadalts.e2e.webservice.core.services.WebServiceObject;
import org.scadalts.e2e.webservice.core.services.WebServiceObjectExecutor;
import org.scadalts.e2e.webservice.core.session.CookieFactory;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.Optional;

@Log4j2
@Builder(access = AccessLevel.PACKAGE)
public class PointValueWebServiceObject implements WebServiceObject {

    private final PointValueParams pointValueParams;
    private final URL baseUrl;
    private final Client client;

    public Optional<E2eResponse<PointValueResponse>> getValue() {
        return WebServiceObjectExecutor.execute(this::_getValue, pointValueParams.getXid());
    }

    public Optional<E2eResponse<PointValueResponse>> getValue(PointValueParams pointValueParams) {
        return WebServiceObjectExecutor.execute(this::_getValue, pointValueParams.getXid());
    }

    private E2eResponse<PointValueResponse> _getValue(String dataPointXid) {
        String endpoint = baseUrl + "/api/point_value/getValue/";
        Cookie cookie = CookieFactory.newSessionCookie(WebServiceObjectConfiguration.sessionId);
        logger.debug("dataPointXid: {}", dataPointXid);
        logger.debug("endpoint: {}", endpoint);
        logger.debug("cookie: {}", cookie);
        Response response = client.target(endpoint)
                .path(dataPointXid)
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
