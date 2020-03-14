package org.scadalts.e2e.test.impl.utils;

import com.codeborne.selenide.Configuration;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.common.config.E2eConfigurator;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.CmpServiceObject;
import org.scadalts.e2e.service.impl.services.LoginServiceObject;
import org.scadalts.e2e.service.impl.services.PointValueServiceObject;
import org.scadalts.e2e.service.impl.services.ServiceObjectFactory;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.login.LoginParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.core.config.TestCoreConfigurator;
import org.scadalts.e2e.test.core.exceptions.E2eAuthenticationException;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.TestImplConfigurator;

import java.util.Optional;

@Log4j2
public class TestWithoutPageUtil {

    private static LoginServiceObject loginServiceObject;

    public static void preparingTest() {
        if(!isLogged()) {
            _setup();
            LoginParams loginParams = LoginParams.builder()
                    .userName(E2eConfiguration.userName)
                    .password(E2eConfiguration.password)
                    .build();
            loginServiceObject = ServiceObjectFactory.newLoginServiceObject();
            E2eResponse<String> response = login(loginParams);
            E2eConfiguration.sessionId = response.getSessionId();
            if(!_isLogged(response)) {
                throw new E2eAuthenticationException(E2eConfiguration.userName);
            }
        }
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
        loginServiceObject = ServiceObjectFactory.newLoginServiceObject();
        Optional<E2eResponse<String>> responseOpt = loginServiceObject.login(cmpParams, Configuration.timeout);
        return responseOpt.orElseGet(E2eResponse::empty);
    }

    public static E2eResponse<CmpParams> setValue(CmpParams cmpParams) {
        try (CmpServiceObject cmpWebServiceObject = ServiceObjectFactory.newCmpServiceObject()) {
            Optional<E2eResponse<CmpParams>> responseOpt = cmpWebServiceObject.set(cmpParams, Configuration.timeout);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<PointValueResponse> getValue(PointValueParams pointValueParams, String expectedValue) {
        try (PointValueServiceObject pointValueWebServiceObject =
                     ServiceObjectFactory.newPointValueServiceObject()) {
            Optional<E2eResponse<PointValueResponse>> responseOpt = pointValueWebServiceObject.getValue(pointValueParams,
                    TestImplConfiguration.waitingAfterSetPointValueMs, expectedValue);
            return responseOpt.orElseGet(E2eResponse::empty);
        }
    }

    public static E2eResponse<PointValueResponse> getValue(PointValueParams pointValueParams) {
        try (PointValueServiceObject pointValueWebServiceObject =
                     ServiceObjectFactory.newPointValueServiceObject()) {
            Optional<E2eResponse<PointValueResponse>> responseOpt = pointValueWebServiceObject.getValue(pointValueParams,
                    TestImplConfiguration.waitingAfterSetPointValueMs);
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

    public static void close() {
        if(loginServiceObject != null) {
            loginServiceObject.close();
        }
    }
}