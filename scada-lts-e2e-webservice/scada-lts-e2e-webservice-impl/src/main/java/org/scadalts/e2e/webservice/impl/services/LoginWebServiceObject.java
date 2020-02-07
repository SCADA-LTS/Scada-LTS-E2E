package org.scadalts.e2e.webservice.impl.services;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.webservice.core.config.WebServiceObjectConfiguration;
import org.scadalts.e2e.webservice.core.services.E2eResponse;
import org.scadalts.e2e.webservice.core.services.WebServiceObject;
import org.scadalts.e2e.webservice.core.services.WebServiceObjectExecutor;
import org.scadalts.e2e.webservice.core.session.CookieFactory;
import org.scadalts.e2e.webservice.impl.services.login.LoginParams;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.Optional;

@Log4j2
@Builder(access = AccessLevel.PACKAGE)
public class LoginWebServiceObject implements WebServiceObject {

    public final URL baseUrl;
    private final Client client;

    public Optional<E2eResponse<String>> login(LoginParams loginParams) {
        return WebServiceObjectExecutor.execute(this::_login, loginParams);
    }

    public Optional<E2eResponse<String>> logout() {
        return WebServiceObjectExecutor.execute(this::_logout);
    }

    @Override
    public void close() {
        logout();
        client.close();
    }

    private E2eResponse<String> _login(LoginParams loginParams) {
        String endpoint = baseUrl + "/login.htm";
        logger.debug("endpoint: {}", endpoint);
        Response response = ClientBuilder.newClient()
                .target(endpoint)
                .queryParam("username", loginParams.getUserName())
                .queryParam("password", loginParams.getPassword())
                .queryParam("submit", loginParams.getSubmit())
                .request(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .post(null);
        E2eResponse<String> res = E2eResponseFactory.newResponse(response, String.class);
        _setConfig(res);
        return res;
    }

    private E2eResponse<String> _logout() {
        String endpoint = baseUrl +"/logout.htm";
        Cookie cookie = CookieFactory.newSessionCookie(WebServiceObjectConfiguration.sessionId);
        logger.debug("endpoint: {}", endpoint);
        logger.debug("cookie: {}", cookie);
        Response response = client.target(endpoint)
                .request(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .cookie(cookie)
                .get();
        return E2eResponseFactory.newResponse(response, String.class);
    }

    private void _setConfig(E2eResponse<String> response) {
        WebServiceObjectConfiguration.sessionId = response.getSessionId();
    }

}
