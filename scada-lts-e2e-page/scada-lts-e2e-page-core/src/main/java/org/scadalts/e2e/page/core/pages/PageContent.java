package org.scadalts.e2e.page.core.pages;

import org.scadalts.e2e.page.core.criteria.ObjectCriteria;

interface PageContent {
    String getHeadHtml();
    String getBodyText();
    String getTitle();
    default boolean containsObject(ObjectCriteria criteria) {
        String bodyText = getBodyText();
        return bodyText.contains(criteria.getName()) &&
                bodyText.contains(criteria.getType().getTypeName());
    }
    default boolean containsText(String text) {
        return getBodyText().contains(text);
    }
}
