package org.scadalts.e2e.webservice.impl.services;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.webservice.core.exceptions.WebServiceObjectException;
import org.scadalts.e2e.webservice.core.services.E2eResponse;
import org.scadalts.e2e.webservice.core.services.E2eResponseFactory;
import org.scadalts.e2e.webservice.core.services.WebServiceObject;
import org.scadalts.e2e.webservice.core.sessions.CookieFactory;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.Optional;

import static org.scadalts.e2e.common.utils.ExecutorUtil.execute;

@Log4j2
@Builder(access = AccessLevel.PACKAGE)
public class PointValueWebServiceObject implements WebServiceObject {

    private final URL baseUrl;
    private final Client client;

    public Optional<E2eResponse<PointValueResponse>> getValue(PointValueParams pointValueParams) throws WebServiceObjectException {
        return Optional.ofNullable(execute(this::_getValue, pointValueParams.getXid(), WebServiceObjectException::new));
    }

    private E2eResponse<PointValueResponse> _getValue(String dataPointXid) {
        String endpoint = baseUrl + "/api/point_value/getValue/";
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("dataPointXid: {}", dataPointXid);
        logger.info("endpoint: {}", endpoint);
        logger.info("cookie: {}", cookie);
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
