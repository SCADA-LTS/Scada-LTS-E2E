package org.scadalts.e2e.test.impl.tests.check.login;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.scadalts.e2e.common.utils.ExecutorUtil.execute;

@Log4j2
public class LoginCheckTest {

    private NavigationPage navigationPage;
    private LoginPage loginPage;

    @Before
    public void setup() throws ConfigureTestException {
        if(E2eAbstractRunnable.isLogged()) {
            navigationPage = E2eAbstractRunnable.getNavigationPage();
            navigationPage.logout();
            //execute(navigationPage::logout, ConfigureTestException::new);
        }
        loginPage = execute(LoginPage::openPage, ConfigureTestException::new).printLoadingMeasure();
    }

    @After
    public void setSessionId() {
        if(navigationPage != null) {
            E2eConfiguration.sessionId = navigationPage.getSessionId().orElse("");
            E2eAbstractRunnable.setNavigationPage(navigationPage);
        }
    }

    @Test
    public void test_login() {

        //when:
        navigationPage = loginPage.maximize()
                .setUserName(E2eConfiguration.userName)
                .setPassword(E2eConfiguration.password)
                .login()
                .printLoadingMeasure()
                .waitOnPage(500);

        //and:
        String userName = navigationPage.getUserName();

        //then:
        assertThat(E2eConfiguration.userName, equalTo(userName));
    }
}
