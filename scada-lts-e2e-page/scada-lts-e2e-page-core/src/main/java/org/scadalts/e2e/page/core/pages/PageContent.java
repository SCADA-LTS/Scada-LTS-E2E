package org.scadalts.e2e.page.core.pages;

import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.utils.RegexFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$;
import static org.scadalts.e2e.page.core.utils.E2eUtil.acceptAlert;
import static org.scadalts.e2e.page.core.utils.E2eUtil.dismissAlert;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhileNotVisible;

interface PageContent<T extends PageObject<T>> extends GetPage<T> {

    String getHeadHtml();
    String getBodyText();
    String getBodyHtml();

    default String getTitle() {
        return waitWhileNotVisible($(".smallTitle")).getText();
    }

    default boolean containsObject(CriteriaObject criteria) {
        String bodyText = getBodyText();
        Pattern pattern = Pattern.compile(RegexFactory.betweenIdentifierType(criteria));
        Matcher matcher = pattern.matcher(bodyText);
        return matcher.find();
    }

    default boolean containsObjectWithoutType(CriteriaObject criteria) {
        String bodyText = getBodyText();
        Pattern pattern = Pattern.compile(RegexFactory.identifier(criteria.getIdentifier()));
        Matcher matcher = pattern.matcher(bodyText);
        return matcher.find();
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
