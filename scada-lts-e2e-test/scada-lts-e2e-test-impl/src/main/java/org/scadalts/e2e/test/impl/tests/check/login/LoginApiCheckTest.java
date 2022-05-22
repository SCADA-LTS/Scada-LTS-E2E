package org.scadalts.e2e.test.impl.tests.check.login;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.scadalts.e2e.common.core.config.E2eConfiguration;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

public class LoginApiCheckTest {

    @After
    public void clean() {
        TestWithoutPageUtil.logout();
    }

    @Test
    public void test_login() {

        //when:
        TestWithoutPageUtil.preparingTest();

        //then:
        Assert.assertTrue("Problem with logging into the system for user: " + E2eConfiguration.username, TestWithoutPageUtil.isApiLogged());
    }
}
