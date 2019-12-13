package org.scadalts.e2e.pages.page;

import com.codeborne.selenide.WebDriverRunner;

public interface Maximizable {
    default void maximize() {
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }
}
