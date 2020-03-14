package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Selenide;

interface Refreshable<T extends PageObject<T>> extends GetPage<T> {
    default T refreshPage() {
        Selenide.refresh();
        return getPage();
    }
}
