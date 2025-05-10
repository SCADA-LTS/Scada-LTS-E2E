package org.scadalts.e2e.service.impl.services;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.config.E2eConfiguration;
import org.scadalts.e2e.common.core.exceptions.ApplicationIsNotAvailableException;
import org.scadalts.e2e.common.core.utils.StabilityUtil;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.core.services.E2eResponseFactory;
import org.scadalts.e2e.service.core.services.WebServiceObject;
import org.scadalts.e2e.service.core.sessions.CookieFactory;
import org.scadalts.e2e.service.impl.services.login.LoginParams;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import static org.scadalts.e2e.service.core.utils.ServiceStabilityUtil.*;

@Log4j2
@Builder(access = AccessLevel.PACKAGE)
public class LoginServiceObject implements WebServiceObject {

    public static final String LOGIN_HTM_ERROR = "/login.htm?error";
    public final URL baseUrl;
    private final Client client;

    public Optional<E2eResponse<String>> login(LoginParams loginParams, long timeout) {
        try {
            E2eResponse<String> response = applyWhile(this::_login, loginParams,
                    new StabilityUtil.Timeout(timeout), a -> !a.getLocation().contains(LOGIN_HTM_ERROR));
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<E2eResponse<String>> loginOrThrowException(LoginParams loginParams, long timeout) {
        try {
            E2eResponse<String> response = applyWhileOrThrowException(this::_login, loginParams,
                    new StabilityUtil.Timeout(timeout), a -> !a.getLocation().contains(LOGIN_HTM_ERROR));
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<E2eResponse<String>> logout(long timeout) {
        try {
            E2eResponse<String> response = executeWhile(this::_logoutForm, new StabilityUtil.Timeout(timeout));
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<E2eResponse<String>> logoutOrThrowException(long timeout) {
        try {
            E2eResponse<String> response = executeWhileOrThrowException(this::_logoutForm, new StabilityUtil.Timeout(timeout));
            return Optional.ofNullable(response);
        } catch (Throwable th) {
            if((th.getCause() instanceof ConnectException)
                    || (th.getCause() instanceof SocketTimeoutException)) {
                throw new ApplicationIsNotAvailableException(th);
            }
            throw th;
        }
    }

    @Override
    public void close() {
        client.close();
    }

    private E2eResponse<String> _login(LoginParams loginParams) {
        try {
            /*E2eResponse<String> res = this._loginOld(loginParams);
            if(res.getStatus() == 302 && res.getSessionId() != null && !res.getSessionId().isEmpty()
                    && !res.getLocation().isEmpty() && !res.getLocation().contains("/login")
                    && !res.getLocation().contains("?error")
                    && res.getHeaders().containsKey("Set-Cookie"))
                return res;
            return _loginForm(loginParams);*/
            return  this._loginOld(loginParams);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return E2eResponse.empty();
        }
    }

    private E2eResponse<String> _loginOld(LoginParams loginParams) {
        String endpoint = baseUrl + "/login.htm";
        logger.info("endpoint: {}", endpoint);
        MultivaluedMap<String, String> data = new MultivaluedHashMap<>();
        data.put("username", Arrays.asList(loginParams.getUserName()));
        data.put("password", Arrays.asList(loginParams.getPassword()));
        Response response = ClientBuilder.newClient()
                .target(endpoint)
                .request()
                .post(Entity.form(data));
        E2eResponse<String> res = E2eResponseFactory.newResponse(response, String.class);
        _setConfig(res);
        return res;
    }

    private E2eResponse<String> _loginForm(LoginParams loginParams) {
        String endpoint = baseUrl + "/login";
        logger.info("endpoint: {}", endpoint);
        MultivaluedMap<String, String> data = new MultivaluedHashMap<>();
        data.put("username", Arrays.asList(loginParams.getUserName()));
        data.put("password", Arrays.asList(loginParams.getPassword()));
        Response response = ClientBuilder.newClient()
                .target(endpoint)
                .request(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .post(Entity.form(data));
        E2eResponse<String> res = E2eResponseFactory.newResponse(response, String.class);
        _setConfig(res);
        return res;
    }

    private E2eResponse<String> _loginBasic(LoginParams loginParams) {
        String endpoint = baseUrl + "/login";
        logger.info("endpoint: {}", endpoint);
        Response response = ClientBuilder.newClient()
                .target(endpoint)
                .request()
                .header("WWW-Authenticate","Basic " + toBase64(loginParams))
                .header("Authorization","Basic " + toBase64(loginParams))
                .post(null);
        E2eResponse<String> res = E2eResponseFactory.newResponse(response, String.class);
        _setConfig(res);
        return res;
    }

    private String toBase64(LoginParams loginParams) {
        return Base64.getEncoder().encodeToString((loginParams.getUserName() + ":" + loginParams.getPassword()).getBytes());
    }

    private E2eResponse<String> _logoutForm() {
        String endpoint = baseUrl + "/logout.htm";
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("endpoint: {}", endpoint);
        logger.info("cookie: {}", cookie);
        Response response = client.target(endpoint)
                .request(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .cookie(cookie)
                .get();
        return E2eResponseFactory.newResponse(response, String.class);
    }

    private void _setConfig(E2eResponse<String> response) {
        E2eConfiguration.sessionId = response.getSessionId();
    }

}
