package org.scadalts.e2e.pages.type;

import com.codeborne.selenide.Selenide;

interface Refreshable {

    default void refreshPage() {
        Selenide.refresh();
    }
}
