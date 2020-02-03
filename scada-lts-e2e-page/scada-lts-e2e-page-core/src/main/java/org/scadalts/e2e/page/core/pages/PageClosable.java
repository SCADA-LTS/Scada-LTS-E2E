package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Selenide;

interface PageClosable {
    default void closeWindows() {
        Selenide.closeWebDriver();
    }
}
