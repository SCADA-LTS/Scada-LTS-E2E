package org.scadalts.e2e.page.core.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.scadalts.e2e.page.core.utils.E2eWebDriverProvider;

import static org.scadalts.e2e.common.core.utils.ExecutorUtil.executeSupplier;

public interface GetUrl {

    Logger LOGGER = LogManager.getLogger(GetUrl.class);

    default String getCurrentUrl() {
        try {
            RemoteWebDriver remoteWebDriver = E2eWebDriverProvider.getDriver();
            return executeSupplier(remoteWebDriver::getCurrentUrl, IllegalStateException::new);
        } catch (Exception ex) {
            LOGGER.warn(ex.getMessage(), ex);
            return "warnning";
        }
    }

    default void printCurrentUrl() {
        String currentUrl = getCurrentUrl();
        if(currentUrl.equalsIgnoreCase("warnning"))
            currentUrl = getCurrentUrl();
        LOGGER.info("current url: {}", currentUrl);
    }
}
