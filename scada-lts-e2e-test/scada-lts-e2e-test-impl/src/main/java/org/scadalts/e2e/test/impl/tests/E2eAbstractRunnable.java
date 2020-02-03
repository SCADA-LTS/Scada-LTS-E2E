package org.scadalts.e2e.test.impl.tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.tests.E2eRunnable;
import org.scadalts.e2e.webservice.api.E2eWebServiceObjectApi;

public abstract class E2eAbstractRunnable implements E2eRunnable {

    private static NavigationPage navigationPage;

    @BeforeClass
    public static void setup() {
        navigationPage = LoginPage.openPage()
                .maximize()
                .printLoadingMeasure()
                .setUserName(E2eConfiguration.userName)
                .setPassword(E2eConfiguration.password)
                .login()
                .printLoadingMeasure();
        String sessionId = navigationPage.getSessionId().orElse("");
        E2eWebServiceObjectApi.newInstance(sessionId).init(null);
    }

    @AfterClass
    public static void logout() {
        navigationPage.logout();
        navigationPage.closeWindows();
        navigationPage = null;
    }

    public static NavigationPage getNavigationPage() {
        return navigationPage;
    }

}
