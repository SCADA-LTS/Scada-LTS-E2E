package org.scadalts.e2e.test.impl.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.pages.PageObject;

import java.text.MessageFormat;

public class ContainsObject extends TypeSafeMatcher<PageObject<?>> {

    private final CriteriaObject criteria;
    private PageObject<?> page;

    ContainsObject(CriteriaObject criteria) {
        this.criteria = criteria;
    }

    @Override
    protected boolean matchesSafely(PageObject<?> page) {
        this.page = page;
        return page.containsObject(criteria);
    }

    @Override
    public void describeTo(Description description) {
        String msg = MessageFormat.format("object: {0}, exists on page: {1}",
                criteria.getIdentifier().getValue(),
                page.getClass().getSimpleName());
        description.appendText(msg);
    }

    public static ContainsObject containsObject(CriteriaObject criteria) {
        return new ContainsObject(criteria);
    }
}
