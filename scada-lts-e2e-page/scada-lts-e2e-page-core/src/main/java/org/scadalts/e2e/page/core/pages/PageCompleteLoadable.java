package org.scadalts.e2e.page.core.pages;

public interface PageCompleteLoadable<T extends PageObject<T>> {
    T waitForCompleteLoad();
}
