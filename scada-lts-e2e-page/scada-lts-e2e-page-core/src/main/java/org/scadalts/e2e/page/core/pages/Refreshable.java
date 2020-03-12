package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Selenide;

interface Refreshable<T extends PageObject<T>> extends GetPage<T>, GetTarget {
    default T refreshPage() {
        Selenide.refresh();
        getTarget().waitWhileNotVisible();
        return getPage();
    }
}
