package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Selenide;

import java.io.Closeable;

public interface PageClosable extends Closeable {
    default void closeWindows() {
        Selenide.closeWebDriver();
    }

    @Override
    default void close() {
        closeWindows();
    }
}
