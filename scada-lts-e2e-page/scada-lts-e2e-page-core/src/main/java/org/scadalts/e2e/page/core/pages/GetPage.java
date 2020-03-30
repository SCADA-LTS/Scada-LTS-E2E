package org.scadalts.e2e.page.core.pages;

interface GetPage<T extends PageObject<T>> {

    T getPage();

    default String getName() {
        return getPage().getClass().getSimpleName();
    }
}
