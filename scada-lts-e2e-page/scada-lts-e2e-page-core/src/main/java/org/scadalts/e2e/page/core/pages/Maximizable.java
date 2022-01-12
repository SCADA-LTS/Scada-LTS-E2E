package org.scadalts.e2e.page.core.pages;

import org.scadalts.e2e.page.core.utils.E2eWebDriverProvider;

interface Maximizable<T extends PageObject<T>> extends GetPage<T>  {
    default T maximize() {
        E2eWebDriverProvider.manage()
                .window()
                .maximize();
        return getPage();
    }

    default int getWindowHeight() {
        return E2eWebDriverProvider.manage()
                .window()
                .getSize().height;
    }

    default int getWindowWidth() {
        return E2eWebDriverProvider.manage()
                .window()
                .getSize().width;
    }
}
