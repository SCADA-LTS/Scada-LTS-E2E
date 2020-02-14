package org.scadalts.e2e.test.impl.tests.check.login;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.scadalts.e2e.common.utils.ExecutorUtil.execute;

@RunWith(E2eTestRunner.class)
public class LogoutCheckTest {

    private NavigationPage navigationPage;

    @Before
    public void setup() throws ConfigureTestException {
        if(E2eAbstractRunnable.isLogged()) {
            navigationPage = E2eAbstractRunnable.getNavigationPage();
            return;
        }
        LoginPage loginPage = execute(LoginPage::openPage, ConfigureTestException::new);
        loginPage.setUserName(E2eConfiguration.userName);
        loginPage.setPassword(E2eConfiguration.password);
        navigationPage = loginPage.login();
    }

    @After
    public void clean() throws ConfigureTestException {
        E2eAbstractRunnable.close();
    }

    @Test
    public void test_logout() {

        //when:
        navigationPage.logout();

        //and:
        String currentUrl = navigationPage.getCurrentUrl();

        //and:
        String body = navigationPage.getBodyText();

        //then:
        assertThat(body, not(containsString(E2eConfiguration.userName)));
        assertThat(E2eConfiguration.baseUrl + LoginPage.getUrlRef(), equalTo(currentUrl));
    }
}
