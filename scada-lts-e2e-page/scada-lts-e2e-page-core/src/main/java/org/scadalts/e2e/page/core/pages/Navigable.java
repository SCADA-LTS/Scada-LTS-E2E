package org.scadalts.e2e.page.core.pages;


import org.scadalts.e2e.page.core.components.E2eWebElement;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

interface Navigable<T extends MainPageObject<T>> extends GetPage<T> {

    E2eWebElement getSource();
    String getRefUrl();

    default T openInNewTab() {
        getSource().openInNewTab();
        T page = getPage();
        page.printCurrentUrl();
        return page;
    }

    default T reopen() {
        try {
            getSource().click();
        } catch (Exception ex) {
            return openPage();
        }
        T page = getPage();
        page.printCurrentUrl();
        if(!page.containsText(page.getTitle()))
            return openPage();
        return page;
    }

    default T reopenPage() {
        try {
            getSource().click();
        } catch (Exception ex) {
            return openPage();
        }
        T page = page(getPage());
        page.printCurrentUrl();
        if(!page.containsText(page.getTitle()))
            return openPage();
        return page;
    }

    default T openPage() {
        T page = open(getRefUrl(), (Class<T>)getPage().getClass());
        page.printCurrentUrl();
        return page;
    }
}
