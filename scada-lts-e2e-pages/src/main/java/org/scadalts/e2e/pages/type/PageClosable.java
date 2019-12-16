package org.scadalts.e2e.pages.type;

import com.codeborne.selenide.Selenide;

public interface PageClosable {

    default void closeWindows() {
        Selenide.closeWindow();
    }
}
