package org.scadalts.e2e.page.core.pages;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scadalts.e2e.page.core.components.E2eWebElement;

import static com.codeborne.selenide.Selenide.page;

interface Navigable<T extends MainPageObject<T>> extends GetPage<T> {

    Logger LOGGER = LogManager.getLogger(Navigable.class);
    E2eWebElement getSource();

    default T openInNewTab() {
        getSource().openInNewTab();
        T page = getPage();
        LOGGER.info("current url: {}", page.getCurrentUrl());
        page.waitForCompleteLoad();
        return page;
    }

    default T reopen() {
        getSource().click();
        T page = getPage();
        LOGGER.info("current url: {}", page.getCurrentUrl());
        page.waitForCompleteLoad();
        return page;
    }

    default T openPage() {
        getSource().click();
        T page = getPage();
        LOGGER.info("current url: {}", page.getCurrentUrl());
        return page(page);
    }
}
