package org.scadalts.e2e.pages.type;

import com.codeborne.selenide.WebDriverRunner;

interface Maximizable {
    default void maximize() {
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }
}
