package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Selenide;

import java.time.LocalDateTime;

public interface GetScreenshot<T extends PageObject<T>> extends GetPage<T> {

    default T screenshot(String fileName) {
        Selenide.screenshot(fileName);
        return getPage();
    }

    default T screenshot() {
        Selenide.screenshot("screenshot_" + LocalDateTime.now());
        return getPage();
    }
}
