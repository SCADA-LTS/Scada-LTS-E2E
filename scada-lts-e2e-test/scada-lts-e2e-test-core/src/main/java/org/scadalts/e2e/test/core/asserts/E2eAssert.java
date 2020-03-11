package org.scadalts.e2e.test.core.asserts;

import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.exceptions.ObjectNotExistsAssertionError;
import org.scadalts.e2e.page.core.pages.PageObject;

import java.util.Set;

public class E2eAssert {

    public static <T extends PageObject<T>, R extends CriteriaObject> void assertExists(PageObject<T> pageObject, R... criteriaObjects) {
        for (CriteriaObject criteriaObject : criteriaObjects) {
            if(!pageObject.containsObject(criteriaObject)) {
                throw new ObjectNotExistsAssertionError(pageObject, criteriaObject);
            }
        }
    }

    public static <T extends PageObject<T>, R extends CriteriaObject> void assertExists(PageObject<T> pageObject, Set<R> criteriaObjects) {
        assertExists(pageObject, criteriaObjects.toArray(new CriteriaObject[]{}));
    }
}
