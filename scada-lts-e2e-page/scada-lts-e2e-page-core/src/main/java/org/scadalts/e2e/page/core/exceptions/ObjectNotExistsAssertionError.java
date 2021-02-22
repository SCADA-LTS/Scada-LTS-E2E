package org.scadalts.e2e.page.core.exceptions;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.pages.PageObject;

import java.text.MessageFormat;

@Log4j2
public class ObjectNotExistsAssertionError extends AssertionError {

    public <T extends PageObject<T>> ObjectNotExistsAssertionError(PageObject<T> pageObject, IdentifierObject identifier) {
        super(MessageFormat.format("No exists: {0}, on page: {1}", identifier.getValue(), getWhere(pageObject)));
    }

    private static <T extends PageObject<T>> String getWhere(PageObject<T> pageObject) {
        try {
            return pageObject.getCurrentUrl();
        } catch (Throwable th) {
            logger.warn(th.getMessage(), th);
            return pageObject.getClass().getSimpleName();
        }
    }
}
