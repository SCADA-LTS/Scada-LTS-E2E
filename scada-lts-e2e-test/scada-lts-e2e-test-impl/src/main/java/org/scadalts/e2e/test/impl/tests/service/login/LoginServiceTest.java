package org.scadalts.e2e.test.impl.tests.service.login;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.common.core.config.E2eConfiguration;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.LoginServiceObject;
import org.scadalts.e2e.service.impl.services.ServiceObjectFactory;
import org.scadalts.e2e.service.impl.services.login.LoginParams;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class LoginServiceTest {

    private LoginServiceObject loginServiceObject;

    @Before
    public void setup() {
        loginServiceObject = ServiceObjectFactory.newLoginServiceObject();
        if(TestWithoutPageUtil.isApiLogged()) {
            loginServiceObject.logout(TestImplConfiguration.timeout);
        }
    }

    @After
    public void logout() {
        if(loginServiceObject != null)
            loginServiceObject.close();
    }

    @Test
    public void test_service_login_then_set_cookie_in_headers() {

        //given:
        LoginParams loginParams = LoginParams.builder()
                .userName(E2eConfiguration.username)
                .password(E2eConfiguration.password)
                .submit("Login")
                .build();

        //when:
        Optional<E2eResponse<String>> response = loginServiceObject.login(loginParams, TestImplConfiguration.timeout);

        //then:
        assertTrue(response.isPresent());
        assertEquals(false, response.get().getLocation().contains("login.htm?error"));
    }

}
