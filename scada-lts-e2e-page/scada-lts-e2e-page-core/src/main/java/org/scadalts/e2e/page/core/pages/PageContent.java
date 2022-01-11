package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.utils.E2eWebDriverProvider;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;

interface PageContent<T extends PageObject<T>> extends GetPage<T> {

    String getHeadHtml();
    String getBodyText();
    String getBodyHtml();

    default String getTitle() {
        return $(By.tagName("title")).getText();
        //return waitWhileNotVisible($(".smallTitle")).getText();
    }

    default boolean containsObject(IdentifierObject identifier) {
        getBodyText();
        return findObject(identifier.getNodeCriteria(), $(By.tagName("body"))).is(Condition.visible);
    }

    default boolean containsObject(CriteriaObject criteria) {
        return containsObject(criteria.getIdentifier());
    }

    default boolean containsText(String text) {
        return getBodyText().contains(text);
    }

    default T acceptAlertOnPage() {
        try {
            switchTo().alert().accept();
        } catch (Throwable ex) {

        }
        return getPage();
    }

    default T acceptAlert() {
        return acceptAlertOnPage();
    }

    default T dismissAlert() {
        return dismissAlertOnPage();
    }

    default T dismissAlertOnPage() {
        try {
            switchTo().alert().dismiss();
        } catch (Throwable ex) {

        }
        return getPage();
    }

    default boolean isAlertOnPage() {
        try {
            switchTo().alert();
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }

    default String getAlertContent() {
        try {
            return switchTo().alert().getText();
        } catch (Throwable ex) {
            return "";
        }
    }
}
