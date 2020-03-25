package org.scadalts.e2e.test.impl.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.pages.PageObject;

import java.text.MessageFormat;

public class ContainsObject extends TypeSafeMatcher<PageObject<?>> {

    private final IdentifierObject identifier;
    private PageObject<?> page;

    ContainsObject(IdentifierObject identifier) {
        this.identifier = identifier;
    }

    @Override
    protected boolean matchesSafely(PageObject<?> page) {
        this.page = page;
        return page.containsObject(identifier);
    }

    @Override
    public void describeTo(Description description) {
        String msg = MessageFormat.format("object: {0}, exists on page: {1}",
                identifier.getValue(),
                page.getClass().getSimpleName());
        description.appendText(msg);
    }

    public static ContainsObject containsObject(IdentifierObject identifier) {
        return new ContainsObject(identifier);
    }
}
