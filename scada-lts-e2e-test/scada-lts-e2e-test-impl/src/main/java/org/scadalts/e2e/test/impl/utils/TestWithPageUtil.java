package org.scadalts.e2e.test.impl.utils;

import com.codeborne.selenide.Configuration;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.TimeoutException;
import org.scadalts.e2e.common.core.config.E2eConfiguration;
import org.scadalts.e2e.common.core.config.E2eConfigurator;
import org.scadalts.e2e.common.core.exceptions.*;
import org.scadalts.e2e.page.core.config.PageObjectConfigurator;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.core.config.ServiceObjectConfigurator;
import org.scadalts.e2e.test.core.config.TestCoreConfigurator;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.TestImplConfigurator;

import java.util.Objects;
import java.util.Optional;

import static org.scadalts.e2e.common.core.utils.ExecutorUtil.executeFunction;
import static org.scadalts.e2e.page.core.utils.MeasurePrinter.print;
import static org.scadalts.e2e.test.core.config.TestCoreConfigurator.isLoginEnabled;

@Log4j2
public class TestWithPageUtil {

    private static NavigationPage navigationPage;

    public static boolean isLogged() {
        if (Objects.isNull(navigationPage))
            return false;
        Optional<String> sessionIdOpt = navigationPage.getSessionId();
        if (sessionIdOpt.isEmpty() || sessionIdOpt.get().isEmpty())
            return false;
        if(!TestWithoutPageUtil.isApiLogged()) {
            return false;
        }
        if(navigationPage.getCurrentUrl() == null || navigationPage.getCurrentUrl().contains("/login.htm") || navigationPage.getCurrentUrl().contains("/login")) {
            TestWithoutPageUtil.logout();
            return false;
        }
        return true;
    }

    public static NavigationPage openNavigationPage(CreatorObject<?, ?> creatorObject, CreatorObject<?, ?>... creatorObjects) {
        if(!TestWithPageUtil.isLogged()) {
            creatorObject.reload();
            for (CreatorObject<?, ?> creator: creatorObjects)
                creator.reload();
            if(TestWithPageUtil.isLogged())
                navigationPage = TestWithPageUtil.getNavigationPage();
            else
                navigationPage = TestWithPageUtil.openNavigationPage();
        }
        return navigationPage;
    }


    public static NavigationPage openNavigationPage() {
        _setup();
        if (isLoginEnabled() && !E2eConfiguration.checkAuthentication)
            close();
        return preparingTest();
    }

    public static NavigationPage getNavigationPage() {
        if (navigationPage == null) {
            throw new TestsNotInitializedException();
        }
        return navigationPage;
    }

    public static void close() {
        try {
            _close();
        } catch (Throwable th) {
            logger.warn(th.getMessage(), th);
        }
    }

    public static void logout() {
        if (navigationPage == null) {
            return;
        }
        navigationPage.logout();
    }

    private static NavigationPage preparingTest() {
        try {
            return _preparingTest();
        } catch (Throwable ex) {
            close();
            logger.warn(ex.getMessage(), ex);
            throw new InitializeTestException(ex);
        }
    }

    private static NavigationPage _preparingTest() {
        if (isLoginEnabled()
                && (!E2eConfiguration.checkAuthentication || !isLogged())) {
            _loginOrThrow();
        }
        return navigationPage;
    }

    private static void _loginOrThrow() {
        _login();
        if (E2eConfiguration.checkAuthentication && !isLogged())
            throw new E2eAuthenticationException(E2eConfiguration.username);
    }

    private static void _close() {
        logger.info("close...");
        try {
            if (isLoginEnabled() && (navigationPage != null && E2eConfiguration.checkAuthentication && isLogged()))
                logout();
        } finally {
            if(navigationPage != null)
                NavigationPage.kill();
            navigationPage = null;
        }
    }

    private static void _login() {
        logger.info("login...");
        LoginPage loginPage = loginPage();
        long navigationStart = loginPage.getNavigationStartMs();

        navigationPage = executeFunction(TestWithPageUtil::_login, loginPage, E2eLoginException::new);

        long responseStart = navigationPage.getResponseStartMs();
        long backendPerformanceMs = _performanceMs(navigationStart, responseStart);
        long frontendPerformanceMs = _performanceMs(responseStart, navigationPage.getDomCompleteMs());

        print(_getNavigationPageName(navigationPage), backendPerformanceMs, frontendPerformanceMs);

        logger.info("cookies: {}", navigationPage.getCookies());
        String sessionId = _getSessionId(navigationPage);

        if (sessionId.isEmpty())
            throw new NoSuchSessionException("sessionId is empty, user: " + E2eConfiguration.username);

        E2eConfiguration.sessionId = sessionId;
        ServiceObjectConfigurator.init(sessionId);

        if (backendPerformanceMs > TestImplConfiguration.timeout) {
            String url = navigationPage.getCurrentUrl();
            close();
            throw new ApplicationTooHighLoadException(url, TestImplConfiguration.timeout);
        }
    }

    private static LoginPage loginPage() {
        return LoginPage.openPage();
    }

    private static NavigationPage _login(LoginPage loginPage) {
        try {
            loginPage.maximize()
                    .printLoadingMeasure();
            String body = loginPage.getBodyText();

            if (body != null && body.contains("Connection refused")) {
                throw new ApplicationIsNotAvailableException(E2eConfiguration.baseUrl + LoginPage.getUrlRef(), "");
            }
            return loginPage.setUserName(E2eConfiguration.username)
                    .setPassword(E2eConfiguration.password)
                    .login();
        } catch (TimeoutException th) {
            throw new ApplicationTooHighLoadException(E2eConfiguration.baseUrl + LoginPage.getUrlRef(), Configuration.pageLoadTimeout);
        } catch (Throwable th) {
            logger.warn(th.getMessage(), th);
            throw new ApplicationIsNotAvailableException(E2eConfiguration.baseUrl + LoginPage.getUrlRef(), "");
        }
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
