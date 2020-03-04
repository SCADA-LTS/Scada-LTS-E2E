package org.scadalts.e2e.test.impl.tests.check.login;

import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable.isLogged;
import static org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable.preparingPageTest;

public class IsLoggedThenOpenLoginPageAndBackCheckTest {

    private NavigationPage navigationPage;

    @Before
    public void setup() {
        if(!isLogged()) {
            preparingPageTest();
        }
    }

    @Test
    public void test_login() {

        //when:
        LoginPage.openPage();

        //and:
        navigationPage = NavigationPage.openPage().waitOnPage(2000);

        //then:
        assertThat(navigationPage.getCurrentUrl(), containsString(NavigationPage.URL_REF));
    }
}
