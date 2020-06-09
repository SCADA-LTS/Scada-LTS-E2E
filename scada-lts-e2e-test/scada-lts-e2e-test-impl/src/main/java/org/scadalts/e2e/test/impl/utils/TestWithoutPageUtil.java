package org.scadalts.e2e.test.impl.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.common.config.E2eConfigurator;
import org.scadalts.e2e.common.exceptions.ApplicationIsNotAvailableException;
import org.scadalts.e2e.common.exceptions.E2eAuthenticationException;
import org.scadalts.e2e.service.core.config.ServiceObjectConfigurator;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.*;
import org.scadalts.e2e.service.impl.services.alarms.AlarmParams;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.login.LoginParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.core.config.TestCoreConfigurator;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.TestImplConfigurator;

import java.util.List;
import java.util.Optional;

import static org.scadalts.e2e.common.utils.ExecutorUtil.executeFunction;

@Log4j2
public class TestWithoutPageUtil {

    public static void preparingTest() {
        if(!isLogged()) {
            _setup();
            _login();
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

    public static E2eResponse<String> login(LoginParams cmpParams) {
        try (LoginServiceObject loginServiceObject = ServiceObjectFactory.newLoginServiceObject()){
            Optional<E2eResponse<String>> responseOpt = loginServiceObject.login(cmpParams, TestImplConfiguration.timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<String> logout() {
        try (LoginServiceObject loginServiceObject = ServiceObjectFactory.newLoginServiceObject()){
            Optional<E2eResponse<String>> responseOpt = loginServiceObject.logout(TestImplConfiguration.timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<CmpParams> setValue(CmpParams cmpParams) {
        try (CmpServiceObject cmpWebServiceObject = ServiceObjectFactory.newCmpServiceObject()) {
            Optional<E2eResponse<CmpParams>> responseOpt = cmpWebServiceObject.set(cmpParams, TestImplConfiguration.timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<PointValueResponse> getValue(PointValueParams pointValueParams, String expectedValue) {
        return getValue(pointValueParams, expectedValue, TestImplConfiguration.timeout);
    }

    public static E2eResponse<PointValueResponse> getValue(PointValueParams pointValueParams, String expectedValue,
                                                           long timeout) {
        try (PointValueServiceObject pointValueWebServiceObject =
                     ServiceObjectFactory.newPointValueServiceObject()) {
            Optional<E2eResponse<PointValueResponse>> responseOpt = pointValueWebServiceObject.getValue(pointValueParams,
                    timeout, expectedValue);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<PointValueResponse> getValue(PointValueParams pointValueParams) {
        return getValue(pointValueParams, TestImplConfiguration.timeout);
    }

    public static E2eResponse<PointValueResponse> getValue(PointValueParams pointValueParams, long timeout) {
        try (PointValueServiceObject pointValueWebServiceObject =
                     ServiceObjectFactory.newPointValueServiceObject()) {
            Optional<E2eResponse<PointValueResponse>> responseOpt = pointValueWebServiceObject.getValue(pointValueParams,
                    timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<List<AlarmResponse>> getLiveAlarms(PaginationParams paginationParams, long timeout) {
        try (StorungsAndAlarmsServiceObject storungsAndAlarmsServiceObject =
                     ServiceObjectFactory.newStorungsAndAlarmsServiceObject()) {
            Optional<E2eResponse<List<AlarmResponse>>> responseOpt = storungsAndAlarmsServiceObject.getLiveAlarms(paginationParams,
                    timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<List<AlarmResponse>> getHistoryAlarms(AlarmParams alarmParams, long timeout) {
        try (StorungsAndAlarmsServiceObject storungsAndAlarmsServiceObject =
                     ServiceObjectFactory.newStorungsAndAlarmsServiceObject()) {
            Optional<E2eResponse<List<AlarmResponse>>> responseOpt = storungsAndAlarmsServiceObject.getHistoryAlarms(alarmParams,
                    timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<String> acknowledgeAlarm(String id, long timeout) {
        try (StorungsAndAlarmsServiceObject storungsAndAlarmsServiceObject =
                     ServiceObjectFactory.newStorungsAndAlarmsServiceObject()) {
            Optional<E2eResponse<String>> responseOpt = storungsAndAlarmsServiceObject.acknowledgeAlarm(id,
                    timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
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

    private static void _login() {
        LoginParams loginParams = LoginParams.builder()
                .userName(E2eConfiguration.userName)
                .password(E2eConfiguration.password)
                .build();

        E2eResponse<String> response = executeFunction(TestWithoutPageUtil::login,loginParams,ApplicationIsNotAvailableException::new);

        E2eConfiguration.sessionId = response.getSessionId();
        ServiceObjectConfigurator.init(E2eConfiguration.sessionId);
        if(!_isLogged(response)) {
            throw new E2eAuthenticationException(E2eConfiguration.userName);
        }
    }
}
