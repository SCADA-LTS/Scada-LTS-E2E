package org.scadalts.e2e.test.core.exceptions;

import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.pages.PageObject;

import java.text.MessageFormat;

public class ObjectNotExistsAssertionError extends AssertionError {

    public <T extends PageObject<T>> ObjectNotExistsAssertionError(PageObject<T> pageObject, CriteriaObject criteriaObject) {
        super(MessageFormat.format("No exists: {0}, on page: {1}", criteriaObject.getIdentifier().getValue(), pageObject.getClass().getSimpleName()));
    }
}
