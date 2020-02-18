package org.scadalts.e2e.page.core.pages;

import org.scadalts.e2e.page.core.criteria.ObjectCriteria;

import static org.scadalts.e2e.page.core.util.E2eUtil.acceptAlert;
import static org.scadalts.e2e.page.core.util.E2eUtil.dismissAlert;

interface PageContent<T extends PageObject<T>> extends GetPage<T> {

    String getHeadHtml();
    String getBodyText();
    String getTitle();

    default boolean containsObject(ObjectCriteria criteria) {
        String bodyText = getBodyText();
        return bodyText.contains(criteria.getIdentifier()) &&
                bodyText.contains(criteria.getType().getName());
    }
    default boolean containsText(String text) {
        return getBodyText().contains(text);
    }

    default T acceptAlertIfExists() {
        acceptAlert();
        return getPage();
    }

    default T dismissAlertIfExists() {
        dismissAlert();
        return getPage();
    }
}
