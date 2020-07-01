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
import org.scadalts.e2e.service.impl.services.storungs.AcknowledgeResponse;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.scadalts.e2e.service.core.utils.ServiceStabilityUtil.applyWhile;
import static org.scadalts.e2e.service.core.utils.ServiceStabilityUtil.applyWhilePredicate;

@Log4j2
@Builder(access = AccessLevel.PACKAGE)
public class StorungsAndAlarmsServiceObject implements WebServiceObject {


    private final URL baseUrl;
    private final Client client;

    public Optional<E2eResponse<List<StorungAlarmResponse>>> getLiveAlarms(PaginationParams alarmParams, long timeout) {
        try {
            E2eResponse<List<StorungAlarmResponse>> response = applyWhile(this::_getLiveAlarms, alarmParams, new StabilityUtil.Timeout(timeout));
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<E2eResponse<List<StorungAlarmResponse>>> getLiveAlarms(PaginationParams alarmParams,
                                                                           Predicate<List<StorungAlarmResponse>> predicate,
                                                                           long timeout) {
        try {
            E2eResponse<List<StorungAlarmResponse>> response = applyWhilePredicate(this::_getLiveAlarms, alarmParams,
                    new StabilityUtil.Timeout(timeout), predicate);
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<E2eResponse<List<StorungAlarmResponse>>> getHistoryAlarms(StorungAlarmParams storungAlarmParams, long timeout) {
        try {
            E2eResponse<List<StorungAlarmResponse>> response = applyWhile(this::_getHistoryAlarms, storungAlarmParams, new StabilityUtil.Timeout(timeout));
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<E2eResponse<AcknowledgeResponse>> acknowledgeAlarm(String id, long timeout) {
        try {
            E2eResponse<AcknowledgeResponse> response = applyWhile(this::_acknowledgeAlarm, id, new StabilityUtil.Timeout(timeout));
            return Optional.ofNullable(response);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public void close() {
        client.close();
    }

    private E2eResponse<List<StorungAlarmResponse>> _getLiveAlarms(PaginationParams paginationParams) {
        String endpoint = MessageFormat.format("{0}/api/alarms/live/{1}/{2}", baseUrl,
                String.valueOf(paginationParams.getOffset()),
                String.valueOf(paginationParams.getLimit()));
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("endpoint: {}", endpoint);
        logger.debug("cookie: {}", cookie);
        Response response = client.target(endpoint)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .cookie(cookie)
                .get();
        List<StorungAlarmResponse> list = _get(response);
        return E2eResponseFactory.newResponse(response, list);
    }

    private E2eResponse<List<StorungAlarmResponse>> _getHistoryAlarms(StorungAlarmParams storungAlarmParams) {
        String endpoint = MessageFormat.format("{0}/api/alarms/history/{1}/{2}/{3}/{4}", baseUrl,
                storungAlarmParams.getDateDay(), storungAlarmParams.getFilter(),
                String.valueOf(storungAlarmParams.getPaginationParams().getOffset()),
                String.valueOf(storungAlarmParams.getPaginationParams().getLimit()));
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("endpoint: {}", endpoint);
        logger.debug("cookie: {}", cookie);
        Response response = client.target(endpoint)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .cookie(cookie)
                .get();
        List<StorungAlarmResponse> list = _get(response);
        return E2eResponseFactory.newResponse(response, list);
    }


    private E2eResponse<AcknowledgeResponse> _acknowledgeAlarm(String id) {
        String endpoint = MessageFormat.format("{0}/api/alarms/acknowledge/{1}", baseUrl, id);
        Cookie cookie = CookieFactory.newSessionCookie(E2eConfiguration.sessionId);
        logger.info("endpoint: {}", endpoint);
        logger.debug("cookie: {}", cookie);
        Response response = client.target(endpoint)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .cookie(cookie)
                .post(null);
        return E2eResponseFactory.newResponse(response, AcknowledgeResponse.class);
    }

    private List<StorungAlarmResponse> _get(Response response) {
        return HttpUtils.isPayloadEmpty(response.getStringHeaders()) ? Collections.emptyList()
                : response.readEntity(new GenericType<List<StorungAlarmResponse>>() {});
    }
}
