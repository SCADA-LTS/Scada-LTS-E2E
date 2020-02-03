package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.scadalts.e2e.page.core.util.E2eWebDriverProvider;

import java.util.Optional;

public interface GetCookie {

    default Optional<String> getSessionId() {
        RemoteWebDriver remoteWebDriver = E2eWebDriverProvider.getDriver()
                .orElseGet(() -> (RemoteWebDriver) WebDriverRunner.getAndCheckWebDriver());
        return remoteWebDriver.manage()
                .getCookies()
                .stream()
                .map(Cookie::getValue)
                .findFirst();
    }
}
