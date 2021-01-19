package org.scadalts.e2e.test.impl.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.common.config.E2eConfigurator;
import org.scadalts.e2e.common.exceptions.ApplicationIsNotAvailableException;
import org.scadalts.e2e.common.exceptions.E2eAuthenticationException;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.service.core.config.ServiceObjectConfigurator;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.*;
import org.scadalts.e2e.service.impl.services.eventdetector.EventDetectorParams;
import org.scadalts.e2e.service.impl.services.eventdetector.EventDetectorPostResponse;
import org.scadalts.e2e.service.impl.services.eventdetector.EventDetectorResponse;
import org.scadalts.e2e.service.impl.services.eventhandler.EventHandlerGetParams;
import org.scadalts.e2e.service.impl.services.eventhandler.EventHandlerPostParams;
import org.scadalts.e2e.service.impl.services.eventhandler.EventHandlerResponse;
import org.scadalts.e2e.service.impl.services.storungs.AcknowledgeResponse;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.datapoint.DataPointPropertiesResponse;
import org.scadalts.e2e.service.impl.services.login.LoginParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.core.config.TestCoreConfigurator;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.TestImplConfigurator;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.scadalts.e2e.common.utils.ExecutorUtil.executeFunction;

@Log4j2
public class TestWithoutPageUtil {

    public static void preparingTest() {
        _setup();
        if(!isLogged()) {
            _login();
            if(!isLogged())
                throw new E2eAuthenticationException(E2eConfiguration.userName);
        }
    }

    public static void close() {
        logout();
    }

    public static boolean isLogged() {
        try(PointValueServiceObject serviceObject = ServiceObjectFactory.newPointValueServiceObject()) {
            PointValueParams pointValueParams = new PointValueParams(TestImplConfiguration.dataPointToReadXid);
            Optional<E2eResponse<PointValueResponse>> response = serviceObject.getValue(pointValueParams, TestImplConfiguration.timeout);
            boolean logged = response.isPresent() && response.get().getStatus() != 401;
            logger.info("is logged: {}", logged);
            logger.debug("response: {}", response);
            return logged;
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return false;
        }
    }

    public static E2eResponse<String> logout() {
        try (LoginServiceObject loginServiceObject = ServiceObjectFactory.newLoginServiceObject()){
            Optional<E2eResponse<String>> responseOpt = loginServiceObject.logout(TestImplConfiguration.timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<CmpParams> setDataPointValue(CmpParams cmpParams) {
        try (CmpServiceObject cmpWebServiceObject = ServiceObjectFactory.newCmpServiceObject()) {
            Optional<E2eResponse<CmpParams>> responseOpt = cmpWebServiceObject.set(cmpParams, TestImplConfiguration.timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<PointValueResponse> getDataPointValue(PointValueParams pointValueParams, String expectedValue) {
        return getDataPointValue(pointValueParams, expectedValue, TestImplConfiguration.timeout);
    }

    public static E2eResponse<PointValueResponse> getDataPointValue(PointValueParams pointValueParams, String expectedValue,
                                                                    long timeout) {
        try (PointValueServiceObject pointValueWebServiceObject =
                     ServiceObjectFactory.newPointValueServiceObject()) {
            Optional<E2eResponse<PointValueResponse>> responseOpt = pointValueWebServiceObject.getValue(pointValueParams,
                    timeout, expectedValue);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<PointValueResponse> getDataPointValue(PointValueParams pointValueParams) {
        return getDataPointValue(pointValueParams, TestImplConfiguration.timeout);
    }

    public static E2eResponse<PointValueResponse> getDataPointValue(PointValueParams pointValueParams, long timeout) {
        try (PointValueServiceObject pointValueWebServiceObject =
                     ServiceObjectFactory.newPointValueServiceObject()) {
            Optional<E2eResponse<PointValueResponse>> responseOpt = pointValueWebServiceObject.getValue(pointValueParams,
                    timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<DataPointPropertiesResponse> getDataPointProperties(PointValueParams pointValueParams) {
        return getDataPointProperties(pointValueParams, TestImplConfiguration.timeout);
    }

    public static E2eResponse<DataPointPropertiesResponse> getDataPointProperties(PointValueParams pointValueParams,
                                                                                  long timeout) {
        try (DataPointServiceObject pointValueWebServiceObject =
                     ServiceObjectFactory.newDataPointServiceObject()) {
            Optional<E2eResponse<DataPointPropertiesResponse>> responseOpt = pointValueWebServiceObject.getConfigurationByXid(pointValueParams,
                    timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<DataPointPropertiesResponse> getDataPointProperties(PointValueParams pointValueParams,
                                                                                  Predicate<DataPointPropertiesResponse> expectedValue) {
        try (DataPointServiceObject pointValueWebServiceObject =
                     ServiceObjectFactory.newDataPointServiceObject()) {
            Optional<E2eResponse<DataPointPropertiesResponse>> responseOpt = pointValueWebServiceObject
                    .getConfigurationByXid(pointValueParams, expectedValue, TestImplConfiguration.timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }


    public static E2eResponse<List<StorungAlarmResponse>> getLiveAlarms(PaginationParams paginationParams, long timeout) {
        try (StorungsAndAlarmsServiceObject storungsAndAlarmsServiceObject =
                     ServiceObjectFactory.newStorungsAndAlarmsServiceObject()) {
            Optional<E2eResponse<List<StorungAlarmResponse>>> responseOpt = storungsAndAlarmsServiceObject.getLiveAlarms(paginationParams,
                    timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<List<StorungAlarmResponse>> getLiveAlarms(PaginationParams paginationParams,
                                                                        Predicate<List<StorungAlarmResponse>> whileNot) {
        try (StorungsAndAlarmsServiceObject storungsAndAlarmsServiceObject =
                     ServiceObjectFactory.newStorungsAndAlarmsServiceObject()) {
            Optional<E2eResponse<List<StorungAlarmResponse>>> responseOpt = storungsAndAlarmsServiceObject.getLiveAlarms(paginationParams,
                    whileNot, TestImplConfiguration.timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<List<StorungAlarmResponse>> getHistoryAlarms(StorungAlarmParams storungAlarmParams, long timeout) {
        try (StorungsAndAlarmsServiceObject storungsAndAlarmsServiceObject =
                     ServiceObjectFactory.newStorungsAndAlarmsServiceObject()) {
            Optional<E2eResponse<List<StorungAlarmResponse>>> responseOpt = storungsAndAlarmsServiceObject.getHistoryAlarms(storungAlarmParams,
                    timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<AcknowledgeResponse> acknowledgeAlarm(String id, long timeout) {
        try (StorungsAndAlarmsServiceObject storungsAndAlarmsServiceObject =
                     ServiceObjectFactory.newStorungsAndAlarmsServiceObject()) {
            Optional<E2eResponse<AcknowledgeResponse>> responseOpt = storungsAndAlarmsServiceObject.acknowledge(id,
                    timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    private static void _login() {
        LoginParams loginParams = LoginParams.builder()
                .userName(E2eConfiguration.userName)
                .password(E2eConfiguration.password)
                .build();

        E2eResponse<String> response = executeFunction(TestWithoutPageUtil::_login,loginParams,ApplicationIsNotAvailableException::new);

        E2eConfiguration.sessionId = response.getSessionId();
        ServiceObjectConfigurator.init(E2eConfiguration.sessionId);
        if(!_isLogged(response)) {
            throw new E2eAuthenticationException(E2eConfiguration.userName);
        }
    }

    private static boolean _isLogged(E2eResponse<String> response) {
        return response.getStatus() == 302;
    }

    private static void _setup() {
        E2eConfigurator.init();
        TestCoreConfigurator.init();
        TestImplConfigurator.init();
    }

    private static E2eResponse<String> _login(LoginParams cmpParams) {
        try (LoginServiceObject loginServiceObject = ServiceObjectFactory.newLoginServiceObject()){
            Optional<E2eResponse<String>> responseOpt = loginServiceObject.login(cmpParams, TestImplConfiguration.timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<List<EventDetectorResponse>> getEventDetectors(EventDetectorParams eventDetectorParams) {
        return getEventDetectors(eventDetectorParams, TestImplConfiguration.timeout);
    }

    public static E2eResponse<List<EventDetectorResponse>> getEventDetectors(EventDetectorParams eventDetectorParams, long timeout) {
        try (EventDetectorServiceObject eventDetectorServiceObject =
                     ServiceObjectFactory.newEventDetectorServiceObject()) {
            Optional<E2eResponse<List<EventDetectorResponse>>> responseOpt = eventDetectorServiceObject.getEventDetectorsByXid(eventDetectorParams,
                    timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<EventDetectorPostResponse> setEventDetector(EventDetectorParams eventDetectorParams) {
        return setEventDetector(eventDetectorParams, TestImplConfiguration.timeout);
    }

    public static E2eResponse<EventDetectorPostResponse> setEventDetector(EventDetectorParams eventDetectorParams, long timeout) {
        try (EventDetectorServiceObject eventDetectorServiceObject =
                     ServiceObjectFactory.newEventDetectorServiceObject()) {
            Optional<E2eResponse<EventDetectorPostResponse>> responseOpt = eventDetectorServiceObject.setEventDetector(eventDetectorParams,
                    timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<List<EventHandlerResponse>> getEventHandlers() {
        return getEventHandlers(TestImplConfiguration.timeout);
    }

    public static E2eResponse<List<EventHandlerResponse>> getEventHandlers(long timeout) {
        try (EventHandlerServiceObject eventHandlerServiceObject =
                     ServiceObjectFactory.newEventHandlerServiceObject()) {
            Optional<E2eResponse<List<EventHandlerResponse>>> responseOpt = eventHandlerServiceObject.getAllEventHandlers(timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<EventHandlerResponse> getEventHandlerByXid(EventHandlerGetParams eventHandlerGetParams) {
        return getEventHandlerByXid(eventHandlerGetParams, TestImplConfiguration.timeout);
    }

    public static E2eResponse<EventHandlerResponse> getEventHandlerByXid(EventHandlerGetParams eventHandlerGetParams, long timeout) {
        try (EventHandlerServiceObject eventHandlerServiceObject =
                     ServiceObjectFactory.newEventHandlerServiceObject()) {
            Optional<E2eResponse<EventHandlerResponse>> responseOpt = eventHandlerServiceObject.getEventHandlerByXid(eventHandlerGetParams, timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<EventHandlerResponse> createEventHandler(EventHandlerPostParams eventHandlerPostParams) {
        return createEventHandler(eventHandlerPostParams, TestImplConfiguration.timeout);
    }

    public static E2eResponse<EventHandlerResponse> createEventHandler(EventHandlerPostParams eventHandlerPostParams, long timeout) {
        try (EventHandlerServiceObject eventHandlerServiceObject =
                     ServiceObjectFactory.newEventHandlerServiceObject()) {
            Optional<E2eResponse<EventHandlerResponse>> responseOpt = eventHandlerServiceObject.createEventHandler(eventHandlerPostParams, timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static int createEventDetectorAndGetId(EventDetectorCriteria eventDetectorCriteria){
        EventDetectorParams eventDetectorParams = prepareEventDetectorParams(eventDetectorCriteria);
        E2eResponse<EventDetectorPostResponse> setResponse = TestWithoutPageUtil.setEventDetector(eventDetectorParams);
        return setResponse.getValue().getId();
    }

    private static EventDetectorParams prepareEventDetectorParams(EventDetectorCriteria eventDetectorCriteria) {
        Xid eventDetectorXid = eventDetectorCriteria.getXid();
        Xid dataPointXid = eventDetectorCriteria.getDataSourcePointCriteria().getDataPoint().getXid();
        int eventDetectorAlarmLevel = Integer.parseInt(eventDetectorCriteria.getAlarmLevel().getId());
        String name = eventDetectorCriteria.getIdentifier().getValue();
        EventDetectorResponse body = EventDetectorResponse.builder()
                .xid(eventDetectorXid.getValue())
                .alias(name)
                .alarmLevel(eventDetectorAlarmLevel)
                .build();
        EventDetectorParams eventDetectorParams = new EventDetectorParams();
        eventDetectorParams.setXid(dataPointXid.getValue());
        eventDetectorParams.setBody(body);
        return eventDetectorParams;
    }






}
