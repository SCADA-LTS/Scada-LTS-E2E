package org.scadalts.e2e.page.core.pages;

import org.scadalts.e2e.page.core.javascripts.JQueryExecutable;

public interface PageObject<T extends PageObject<T>> extends Maximizable<T>, Waitable<T>,
        Refreshable<T>, PageLoadingTimeMeasurable<T>, PageContent<T>, GetScreenshot<T>,
        HumanSimulation<T>, PageCompleteLoadable<T>, GetCookie, GetUrl, JQueryExecutable {
}
