package org.scadalts.e2e.test.impl.tests.check.login;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(TestWithPageRunner.class)
public class LogoutCheckTest {

    private NavigationPage navigationPage;

    @Before
    public void setup() {
        if(TestWithPageUtil.isLogged()) {
            navigationPage = TestWithPageUtil.getNavigationPage();
            return;
        }
        LoginPage loginPage = LoginPage.openPage();
        loginPage.setUserName(E2eConfiguration.userName);
        loginPage.setPassword(E2eConfiguration.password);
        navigationPage = loginPage.login();
    }

    @After
    public void clean() {
        TestWithPageUtil.close();
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
