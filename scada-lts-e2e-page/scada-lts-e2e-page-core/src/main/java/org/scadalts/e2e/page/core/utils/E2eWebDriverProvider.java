package org.scadalts.e2e.page.core.utils;

import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Objects;
import java.util.Optional;

@Log4j2
public class E2eWebDriverProvider {

    public static Optional<RemoteWebDriver> getDriver() {
        try {
            RemoteWebDriver webDriver = (RemoteWebDriver) WebDriverRunner.getAndCheckWebDriver();
            if(Objects.isNull(webDriver))
                return Optional.empty();
            return Optional.of(webDriver);
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            return Optional.empty();
        }
    }
}
