package org.scadalts.e2e.test.impl.tests;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.common.config.E2eConfigurator;
import org.scadalts.e2e.page.core.config.PageObjectConfigurator;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.config.TestCoreConfigurator;
import org.scadalts.e2e.test.core.tests.E2eRunnable;
import org.scadalts.e2e.test.impl.config.TestImplConfigurator;
import org.scadalts.e2e.test.impl.utils.ServiceTestsUtil;

import java.util.Optional;

@Log4j2
public abstract class E2eAbstractRunnable implements E2eRunnable {

    private static NavigationPage navigationPage;

    public static void close() {
        _close();
    }

    public static boolean isLogged() {
        if(navigationPage == null)
            return false;
        Optional<String> sessionIdOpt = navigationPage.getSessionId();
        if(!sessionIdOpt.isPresent() || sessionIdOpt.get().isEmpty())
            return false;
        return ServiceTestsUtil.isLogged();
    }

    public static NavigationPage getNavigationPage() {
        return navigationPage;
    }

    public static void preparingPageTest() {
        if (!isLogged()) {
            _setup();
            _login();
        }
        NavigationPage.openPage();
    }

    private static void _login() {
        logger.info("login...");
        NavigationPage navigationPage = LoginPage.openPage()
                .maximize()
                .printLoadingMeasure()
                .setUserName(E2eConfiguration.userName)
                .setPassword(E2eConfiguration.password)
                .login()
                .printLoadingMeasure();
        E2eAbstractRunnable.navigationPage = navigationPage;
        logger.info("cookies: {}", navigationPage.getCookies());
        E2eConfiguration.sessionId = navigationPage.getSessionId().orElse("");
    }

    private static void _setup() {
        E2eConfigurator.init();
        TestCoreConfigurator.init();
        TestImplConfigurator.init();
        PageObjectConfigurator.init();
    }

    private static void _close() {
        if(navigationPage != null) {
            logger.info("close...");
            NavigationPage.kill();
            navigationPage = null;
        }
    }
}
