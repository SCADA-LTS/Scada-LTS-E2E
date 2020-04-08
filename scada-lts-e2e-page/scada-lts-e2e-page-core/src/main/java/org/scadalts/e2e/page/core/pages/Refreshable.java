package org.scadalts.e2e.page.core.pages;

import org.scadalts.e2e.page.core.utils.E2eWebDriverProvider;

interface Refreshable<T extends PageObject<T>> extends GetPage<T> {
    default T refreshPage() {
        E2eWebDriverProvider.navigate().refresh();
        return getPage();
    }
}
