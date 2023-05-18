package org.scadalts.e2e.service.core.services.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.config.E2eConfiguration;
import org.scadalts.e2e.common.core.utils.StabilityUtil;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.core.services.E2eResponseFactory;
import org.scadalts.e2e.service.core.services.WebServiceObject;
import org.scadalts.e2e.service.core.sessions.CookieFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.scadalts.e2e.service.core.utils.ServiceStabilityUtil.*;

@Log4j2
@Builder(access = AccessLevel.PACKAGE)
public class GetServiceObject implements WebServiceObject {

    private final URL baseUrl;
    private final Client client;


    public <T> Optional<E2eResponse<T>> get(GetConfig getConfig, long timeout, Class<T> clazz) {
        return _get(getConfig, timeout, a -> {
            try {
                return new ObjectMapper().readValue(a, clazz);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public <T> Optional<E2eResponse<Map<String, T>>> getMap(GetConfig getConfig, long timeout, Class<T> token) {
        return _get(getConfig, timeout, a -> {
            try {
                return new ObjectMapper().readValue(a, new TypeReference<Map<String, T>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public <T> Optional<E2eResponse<List<T>>> getList(GetConfig getConfig, long timeout, Class<T> token) {
        return _get(getConfig, timeout, a -> {
            try {
                return new ObjectMapper().readValue(a, new TypeReference<List<T>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> Optional<E2eResponse<T>> _get(GetConfig getConfig, long timeout, Function<String, T> mapper) {
        try {
            E2eResponse<T> response = applyWhile(this::_get, getConfig, mapper,
                    new StabilityUtil.Timeout(timeout), a -> a.getStatus() == 200);
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    private <T> E2eResponse<T> _get(GetConfig getConfig, Function<String, T> mapper) {
        String endpoint = baseUrl + getConfig.getEndpoint();
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("endpoint: {}", endpoint);
        logger.info("cookie: {}", cookie);
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        Response response = client
                .target(endpoint)
                .request(mediaType)
                .cookie(cookie)
                .get();
        return E2eResponseFactory.newResponse(response,mapper.apply(response.readEntity(String.class)));
    }

    @Override
    public void close() {
        client.close();
    }
}
