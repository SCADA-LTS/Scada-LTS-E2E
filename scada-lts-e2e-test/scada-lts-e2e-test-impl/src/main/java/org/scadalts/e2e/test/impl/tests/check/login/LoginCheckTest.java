package org.scadalts.e2e.test.impl.tests.check.login;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.common.core.config.E2eConfiguration;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginCheckTest {

    private NavigationPage navigationPage;

    @Before
    public void setup() {
        TestWithPageUtil.logout();
    }

    @After
    public void clean() {
        TestWithPageUtil.logout();
    }

    @Test
    public void test_login() {

        //when:
        navigationPage = TestWithPageUtil.openNavigationPage();

        //and:
        String userName = navigationPage.getUserName();

        //then:
        assertThat("Problem with logging into the system for user: " + E2eConfiguration.userName, E2eConfiguration.userName, equalTo(userName));
    }
}
