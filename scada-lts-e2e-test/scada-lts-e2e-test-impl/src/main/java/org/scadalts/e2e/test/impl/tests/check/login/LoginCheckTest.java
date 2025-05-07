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

    @Before
    public void setup() {
        TestWithPageUtil.close();
    }

    @After
    public void clean() {
        TestWithPageUtil.close();
    }

    @Test
    public void test_login() {

        //when:
        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage().acceptAlertOnPage().waitOnPage(500);

        //and:
        String userName = navigationPage.getUserName();

        //then:
        assertThat("Problem with logging into the system for user: " + E2eConfiguration.username, userName, equalTo(E2eConfiguration.username));
    }
}
