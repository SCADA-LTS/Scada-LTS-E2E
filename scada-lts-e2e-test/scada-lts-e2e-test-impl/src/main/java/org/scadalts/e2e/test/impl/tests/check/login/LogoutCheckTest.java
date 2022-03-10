package org.scadalts.e2e.test.impl.tests.check.login;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.common.core.config.E2eConfiguration;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogoutCheckTest {

    private NavigationPage navigationPage;

    @Before
    public void setup() {
        if(TestWithPageUtil.isLogged()) {
            navigationPage = TestWithPageUtil.getNavigationPage();
            return;
        }
        navigationPage = TestWithPageUtil.openNavigationPage();
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
        assertThat(body, not(containsString(E2eConfiguration.username)));
        assertThat(currentUrl, anyOf(equalTo(E2eConfiguration.baseUrl + LoginPage.getUrlRef()), equalTo(E2eConfiguration.baseUrl + LoginPage.getUrlRef() + "?logout")));
    }
}
