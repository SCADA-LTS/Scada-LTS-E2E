package org.scadalts.e2e.test.impl.tests.check.login;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginCheckTest {

    private NavigationPage navigationPage;
    private LoginPage loginPage;

    @Before
    public void setup() {
        if(TestWithPageUtil.isLogged()) {
            navigationPage = TestWithPageUtil.getNavigationPage();
            navigationPage.logout();
        }
        loginPage = LoginPage.openPage();
    }

    @After
    public void setNavigationPage() {
        if(Objects.nonNull(navigationPage))
            TestWithPageUtil.initNavigationPage(navigationPage);
    }

    @Test
    public void test_login() {

        //when:
        navigationPage = loginPage.maximize()
                .printLoadingMeasure()
                .setUserName(E2eConfiguration.userName)
                .setPassword(E2eConfiguration.password)
                .login()
                .printLoadingMeasure();

        //and:
        String userName = navigationPage.getUserName();

        //then:
        assertThat(E2eConfiguration.userName, equalTo(userName));
    }
}
