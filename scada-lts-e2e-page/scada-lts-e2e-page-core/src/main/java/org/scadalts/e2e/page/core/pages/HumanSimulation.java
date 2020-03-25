package org.scadalts.e2e.page.core.pages;

import org.scadalts.e2e.page.core.config.PageConfiguration;

public interface HumanSimulation<T extends PageObject<T>> extends Waitable<T> {

    default T delay() {
        waitOnPage(PageConfiguration.clickDelayMs);
        return getPage();
    }
}
