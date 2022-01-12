package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Selenide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.scadalts.e2e.page.core.utils.E2eWebDriverProvider;

import static org.scadalts.e2e.common.core.utils.ExecutorUtil.executeSupplier;

public interface GetUrl<T extends PageObject<T>> extends PageCompleteLoadable<T>{

    Logger LOGGER = LogManager.getLogger(GetUrl.class);

    default String getCurrentUrl() {
        try {
            Selenide.sleep(100);
            waitForCompleteLoad();
            RemoteWebDriver remoteWebDriver = E2eWebDriverProvider.getDriver();
            return executeSupplier(remoteWebDriver::getCurrentUrl, IllegalStateException::new);
        } catch (Exception ex) {
            LOGGER.warn(ex);
            return "warnning";
        }
    }

    default void printCurrentUrl() {
        String currentUrl = getCurrentUrl();
        if(currentUrl.equalsIgnoreCase("warnning")) {
            Selenide.sleep(500);
            currentUrl = getCurrentUrl();
        }
        LOGGER.info("current url: {}", currentUrl);
    }
}
