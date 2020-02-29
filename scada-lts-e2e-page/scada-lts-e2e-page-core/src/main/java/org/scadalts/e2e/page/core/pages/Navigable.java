package org.scadalts.e2e.page.core.pages;


import org.scadalts.e2e.page.core.components.E2eWebElement;

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
