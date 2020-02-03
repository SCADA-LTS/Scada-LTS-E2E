package org.scadalts.e2e.test.impl.tests.check.login;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginCheckTest {

    private NavigationPage homePage = E2eAbstractRunnable.getNavigationPage();

    @Before
    public void setup() {
        homePage.logout();
    }

    @After
    public void end() {
        homePage.logout();
        E2eAbstractRunnable.setup();
    }

    @Test
    public void test_login() {

        //when:
        homePage = LoginPage.openPage()
                .setUserName(E2eConfiguration.userName)
                .setPassword(E2eConfiguration.password)
                .login();

        //and:
        String userName = homePage.getUserName();

        //then:
        assertThat(E2eConfiguration.userName, equalTo(userName));
    }
}
