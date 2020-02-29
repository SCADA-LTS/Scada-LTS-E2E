package org.scadalts.e2e.test.impl.tests;

import com.codeborne.selenide.Configuration;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.core.config.PageObjectConfigurator;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.PointValueServiceObject;
import org.scadalts.e2e.service.impl.services.ServiceObjectFactory;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;
import org.scadalts.e2e.test.core.config.TestCoreConfigurator;
import org.scadalts.e2e.test.core.tests.E2eRunnable;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.TestImplConfigurator;

import java.util.Optional;

@Log4j2
public abstract class E2eAbstractRunnable implements E2eRunnable {

    private static NavigationPage navigationPage;

    public static void setup() {
        _setup();
        _login();
    }

    public static void close() {
        _close();
    }

    public static boolean isLogged() {
        if(navigationPage == null)
            return false;
        Optional<String> sessionIdOpt = navigationPage.getSessionId();
        if(!sessionIdOpt.isPresent() || sessionIdOpt.get().isEmpty() || sessionIdOpt.get().equals("500"))
            return false;
        return _isLogged();
    }

    public static NavigationPage getNavigationPage() {
        return navigationPage;
    }

    public static void init(NavigationPage navigationPage) {
        E2eAbstractRunnable.navigationPage = navigationPage;
        logger.info("cookies: {}", navigationPage.getCookies());
        E2eConfiguration.sessionId = navigationPage.getSessionId().orElse("");
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
        init(navigationPage);
    }

    private static void _setup() {
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

    private static boolean _isLogged() {
        try(PointValueServiceObject serviceObject = ServiceObjectFactory.newPointValueServiceObject()) {
            PointValueParams pointValueParams = new PointValueParams(TestImplConfiguration.dataPointToReadXid);
            Optional<E2eResponse<PointValueResponse>> response = serviceObject.getValue(pointValueParams, Configuration.timeout);
            logger.info("response: {}", response);
            return response.isPresent() && response.get().getStatus() != 401;
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return false;
        }
    }
}
