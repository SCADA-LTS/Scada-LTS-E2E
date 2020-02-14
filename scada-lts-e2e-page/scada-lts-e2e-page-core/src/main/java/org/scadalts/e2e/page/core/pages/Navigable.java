package org.scadalts.e2e.page.core.pages;


import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.scadalts.e2e.page.core.component.E2eWebElement;
import org.scadalts.e2e.page.core.util.E2eWebDriverProvider;

import static com.codeborne.selenide.Selenide.page;

interface Navigable<T extends MainPageObject<T>> extends GetPage<T> {

    E2eWebElement getSource();

    default T openInNewTab() {
        getSource().openInNewTab();
        return getPage();
    }

    default T reopen() {
        getSource().click();
        return getPage();
    }

    default T openPage() {
        getSource().click();
        return page(getPage());
    }

}
