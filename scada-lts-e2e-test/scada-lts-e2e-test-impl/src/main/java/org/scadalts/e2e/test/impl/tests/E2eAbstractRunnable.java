package org.scadalts.e2e.test.impl.tests;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.core.config.PageObjectConfigurator;
import org.scadalts.e2e.page.impl.pages.LoginPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.config.TestCoreConfigurator;
import org.scadalts.e2e.test.core.tests.E2eRunnable;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.TestImplConfigurator;
import org.scadalts.e2e.webservice.core.services.E2eResponse;
import org.scadalts.e2e.webservice.impl.services.PointValueWebServiceObject;
import org.scadalts.e2e.webservice.impl.services.WebServiceObjectFactory;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueResponse;

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
        return _isLogged();
    }

    public static NavigationPage getNavigationPage() {
        return navigationPage;
    }

    public static void setNavigationPage(NavigationPage navigationPage) {
        E2eAbstractRunnable.navigationPage = navigationPage;
        E2eConfiguration.sessionId = navigationPage.getSessionId().orElse("");
        logger.info("sessionId: {}", E2eConfiguration.sessionId);
    }

    private static void _login() {
        logger.debug("login...");
        NavigationPage navigationPage = LoginPage.openPage()
                .maximize()
                .printLoadingMeasure()
                .setUserName(E2eConfiguration.userName)
                .setPassword(E2eConfiguration.password)
                .login()
                .printLoadingMeasure();
        setNavigationPage(navigationPage);
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

    private static boolean _isLogged() {
        try(PointValueWebServiceObject serviceObject = WebServiceObjectFactory.newPointValueWebServiceObject()) {
            PointValueParams pointValueParams = new PointValueParams(TestImplConfiguration.dataPointToReadXid);
            Optional<E2eResponse<PointValueResponse>> response = serviceObject.getValue(pointValueParams);
            return response.isPresent() && response.get().getStatus() != 401;
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return false;
        }
    }
}
