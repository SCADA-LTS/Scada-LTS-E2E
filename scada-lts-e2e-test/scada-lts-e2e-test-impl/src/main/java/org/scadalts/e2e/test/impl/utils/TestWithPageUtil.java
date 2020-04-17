package org.scadalts.e2e.test.impl.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.common.config.E2eConfigurator;
import org.scadalts.e2e.common.exceptions.ApplicationIsNotAvailableException;
import org.scadalts.e2e.common.exceptions.ApplicationTooHighLoadException;
import org.scadalts.e2e.common.exceptions.E2eAuthenticationException;
import org.scadalts.e2e.page.core.config.PageObjectConfigurator;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.core.config.ServiceObjectConfigurator;
import org.scadalts.e2e.test.core.config.TestCoreConfigurator;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.TestImplConfigurator;

import java.util.Objects;
import java.util.Optional;

import static org.scadalts.e2e.common.utils.ExecutorUtil.executeConsumer;
import static org.scadalts.e2e.page.core.utils.MeasurePrinter.print;

@Log4j2
public class TestWithPageUtil {

    private static NavigationPage navigationPage;

    public static boolean isLogged() {
        if(Objects.isNull(navigationPage))
            return false;
        Optional<String> sessionIdOpt = navigationPage.getSessionId();
        if(!sessionIdOpt.isPresent() || sessionIdOpt.get().isEmpty())
            return false;
        return TestWithoutPageUtil.isLogged();
    }

    public static NavigationPage getNavigationPage() {
        return navigationPage;
    }

    public static void initNavigationPage(NavigationPage navigationPage) {
        TestWithPageUtil.navigationPage = navigationPage;
        E2eConfiguration.sessionId = navigationPage.getSessionId().orElse("");
        ServiceObjectConfigurator.init(E2eConfiguration.sessionId);
    }

    public static NavigationPage preparingTest() {
        if(!isLogged()) {
            _setup();
            _login();
            if(!isLogged())
                throw new E2eAuthenticationException(E2eConfiguration.userName);
        }
        return NavigationPage.openPage();
    }

    public static void close() {
        logger.info("close...");
        try{
            if(navigationPage != null) {
                if(isLogged()) {
                    navigationPage.logout();
                }
            }
        } finally {
            navigationPage = null;
            NavigationPage.kill();
        }
    }

    private static void _login() {
        logger.info("login...");
        LoginPage loginPage = LoginPage.openPage();
        long navigationStart = loginPage.getNavigationStartMs();

        executeConsumer(TestWithPageUtil::_login, loginPage, ApplicationIsNotAvailableException::new);

        long responseStart = navigationPage.getResponseStartMs();
        long backendPerformanceMs = _performanceMs(navigationStart, responseStart);
        long frontendPerformanceMs = _performanceMs(responseStart, navigationPage.getDomCompleteMs());

        print(_getNavigationPageName(navigationPage), backendPerformanceMs, frontendPerformanceMs);

        logger.info("cookies: {}", navigationPage.getCookies());
        E2eConfiguration.sessionId = _getSessionId(navigationPage);

        ServiceObjectConfigurator.init(E2eConfiguration.sessionId);

        if(backendPerformanceMs > TestImplConfiguration.timeout) {
            close();
            throw new ApplicationTooHighLoadException();
        }
    }

    private static void _login(LoginPage loginPage) {
        navigationPage = loginPage
                .maximize()
                .printLoadingMeasure()
                .setUserName(E2eConfiguration.userName)
                .setPassword(E2eConfiguration.password)
                .login();
    }

    private static String _getSessionId(NavigationPage navigationPage) {
        return navigationPage.getSessionId().orElse("");
    }

    private static long _performanceMs(long navigationStart, long responseStart) {
        return responseStart - navigationStart;
    }

    private static String _getNavigationPageName(NavigationPage navigationPage) {
        return navigationPage.getClass().getInterfaces()[0].getSimpleName();
    }

    private static void _setup() {
        E2eConfigurator.init();
        TestCoreConfigurator.init();
        TestImplConfigurator.init();
        PageObjectConfigurator.init();
    }
}
