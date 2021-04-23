package org.scadalts.e2e.test.core.creators;

import org.scadalts.e2e.page.core.pages.PageObject;

public interface CreatorObject<D extends PageObject<D>, C extends PageObject<C>> {
    D deleteObjects();
    C createObjects();
    C openPage();
}
