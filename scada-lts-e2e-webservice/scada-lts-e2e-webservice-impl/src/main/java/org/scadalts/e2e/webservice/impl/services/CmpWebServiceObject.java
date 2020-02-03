package org.scadalts.e2e.webservice.impl.services;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.webservice.core.config.WebServiceObjectConfiguration;
import org.scadalts.e2e.webservice.core.services.E2eResponse;
import org.scadalts.e2e.webservice.core.services.WebServiceObject;
import org.scadalts.e2e.webservice.core.services.WebServiceObjectExecutor;
import org.scadalts.e2e.webservice.core.session.CookieFactory;
import org.scadalts.e2e.webservice.impl.services.cmp.CmpParams;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Log4j2
@Builder(access = AccessLevel.PACKAGE)
public class CmpWebServiceObject implements WebServiceObject {

    private final CmpParams cmpParams;
    public final URL baseUrl;
    private final Client client;

    public Optional<E2eResponse<CmpParams>> set() {
        return WebServiceObjectExecutor.execute(this::_set, cmpParams);
    }

    private E2eResponse<CmpParams> _set(CmpParams cmpParams) {
        String endpoint = baseUrl + "/api/cmp/set/";
        Cookie cookie = CookieFactory.newSessionCookie(WebServiceObjectConfiguration.sessionId);
        logger.debug("params: {}", cmpParams);
        logger.debug("endpoint: {}", endpoint);
        logger.debug("cookie: {}", cookie);
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        Response response = client
                .target(endpoint)
                .path(cmpParams.getXid())
                .path(String.valueOf(cmpParams.getValue()))
                .request(mediaType)
                .cookie(cookie)
                .post(Entity.entity(new CmpParams[]{cmpParams}, mediaType));
        List<CmpParams> para = response.readEntity(new GenericType<List<CmpParams>>(){});
        return E2eResponseFactory.newResponseForJsonArrayFirst(response, para);
    }

    @Override
    public void close() {
        client.close();
    }
}
