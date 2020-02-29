package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.scadalts.e2e.page.core.utils.E2eCookie;
import org.scadalts.e2e.page.core.utils.E2eWebDriverProvider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface GetCookie {

    default Optional<String> getSessionId() {
        RemoteWebDriver remoteWebDriver = E2eWebDriverProvider.getDriver()
                .orElseGet(() -> (RemoteWebDriver) WebDriverRunner.getAndCheckWebDriver());
        return remoteWebDriver.manage()
                .getCookies()
                .stream()
                .filter(a -> a.getName().equals("JSESSIONID"))
                .map(Cookie::getValue)
                .findFirst();
    }

    default List<E2eCookie> getCookies() {
        RemoteWebDriver remoteWebDriver = E2eWebDriverProvider.getDriver()
                .orElseGet(() -> (RemoteWebDriver) WebDriverRunner.getAndCheckWebDriver());
        return remoteWebDriver.manage()
                .getCookies()
                .stream()
                .map(E2eCookie::new)
                .collect(Collectors.toList());
    }
}
