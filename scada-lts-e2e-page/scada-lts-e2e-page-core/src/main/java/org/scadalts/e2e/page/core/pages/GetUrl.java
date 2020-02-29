package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.scadalts.e2e.page.core.utils.E2eWebDriverProvider;

public interface GetUrl {

    default String getCurrentUrl() {
        return E2eWebDriverProvider.getDriver()
                .orElse((RemoteWebDriver) WebDriverRunner.getAndCheckWebDriver())
                .getCurrentUrl();
    }
}
