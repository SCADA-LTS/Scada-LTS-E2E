package org.scadalts.e2e.pages.type;

import com.codeborne.selenide.Selenide;

interface Waitable {

    default void waitOnPage(long wait) {
        Selenide.sleep(wait);
    }

}
