package org.scadalts.e2e.test.core.asserts;

import com.codeborne.selenide.Selenide;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.exceptions.ObjectNotExistsAssertionError;
import org.scadalts.e2e.page.core.pages.PageObject;

import java.util.Set;

public class E2eAssert {

    public static <T extends PageObject<T>, R extends IdentifierObject> void assertExists(PageObject<T> page, R... identifiers) {
        for (IdentifierObject identifier : identifiers) {
            if(!page.containsObject(identifier)) {
                throw new ObjectNotExistsAssertionError(page, identifier);
            }
        }
    }

    public static <T extends PageObject<T>, R extends IdentifierObject> void assertExists(PageObject<T> page, Set<R> identifiers) {
        assertExists(page, identifiers.toArray(new IdentifierObject[]{}));
    }
}
