package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.WebDriverRunner;

interface Maximizable<T extends PageObject<T>> extends GetPage<T>  {
    default T maximize() {
        WebDriverRunner.getWebDriver().manage().window().maximize();
        return getPage();
    }
}
