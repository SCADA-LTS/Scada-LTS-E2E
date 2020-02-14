package org.scadalts.e2e.test.impl.tests;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.core.config.PageObjectConfigurator;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.config.TestCoreConfigurator;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.core.tests.E2eRunnable;
import org.scadalts.e2e.test.impl.config.TestImplConfigurator;
import org.scadalts.e2e.webservice.core.config.WebServiceObjectConfigurator;

import static org.scadalts.e2e.common.utils.ExecutorUtil.execute;

@Log4j2
public abstract class E2eAbstractRunnable implements E2eRunnable {

    private static NavigationPage navigationPage;

    @BeforeClass
    public static void setup() throws ConfigureTestException {
        execute(E2eAbstractRunnable::_setup, ConfigureTestException::new);
        execute(E2eAbstractRunnable::_login, e -> new ConfigureTestException("Not logged in", e));
    }

    @AfterClass
    public static void close() throws ConfigureTestException {
        execute(E2eAbstractRunnable::_close, ConfigureTestException::new);
    }

    private static void _login() {
        logger.debug("login...");
        navigationPage = LoginPage.openPage()
                .maximize()
                .printLoadingMeasure()
                .setUserName(E2eConfiguration.userName)
                .setPassword(E2eConfiguration.password)
                .login()
                .printLoadingMeasure();
        E2eConfiguration.sessionId = navigationPage.getSessionId().orElse("");
        WebServiceObjectConfigurator.init(null, E2eConfiguration.sessionId);
    }

    private static void _setup() {
        TestCoreConfigurator.init();
        TestImplConfigurator.init();
        PageObjectConfigurator.init();
    }

    private static void _close() {
        if(navigationPage != null) {
            logger.debug("close...");
            navigationPage.closeWindows();
            navigationPage = null;
        }
    }

    public static boolean isLogged() {
        return navigationPage != null;
    }

    public static NavigationPage getNavigationPage() {
        return navigationPage;
    }
}
