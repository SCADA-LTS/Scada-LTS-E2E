package org.scadalts.e2e.page.core.exceptions;

import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.pages.PageObject;

import java.text.MessageFormat;

public class ObjectNotExistsAssertionError extends AssertionError {

    public <T extends PageObject<T>> ObjectNotExistsAssertionError(PageObject<T> pageObject, IdentifierObject identifier) {
        super(MessageFormat.format("No exists: {0}, on page: {1}", identifier.getValue(), pageObject.getClass().getSimpleName()));
    }
}
