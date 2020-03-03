package org.scadalts.e2e.test.core.creators;

import org.scadalts.e2e.page.core.pages.PageObject;

public interface CreatorObject<T extends PageObject<T>, R extends PageObject<R>> {
    T deleteObjects();
    R createObjects();
    T openPage();
}
