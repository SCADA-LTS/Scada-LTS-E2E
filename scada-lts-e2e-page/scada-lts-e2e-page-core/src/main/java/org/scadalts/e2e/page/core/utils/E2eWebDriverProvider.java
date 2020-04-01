package org.scadalts.e2e.page.core.utils;

import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Objects;

@Log4j2
public class E2eWebDriverProvider {

    public static RemoteWebDriver getDriver() {
        try {
            RemoteWebDriver webDriver = (RemoteWebDriver) WebDriverRunner.getWebDriver();
            if(Objects.isNull(webDriver))
                throw new IllegalStateException("RemoteWebDriver is null");
            return webDriver;
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            throw throwable;
        }
    }

    public static WebDriver.Options manage() {
        try {
            return getDriver().manage();
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            throw throwable;
        }
    }
}
