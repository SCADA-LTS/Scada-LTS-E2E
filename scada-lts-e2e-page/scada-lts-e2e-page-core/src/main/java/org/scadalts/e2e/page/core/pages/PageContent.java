package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhileNotVisible;

interface PageContent<T extends PageObject<T>> extends GetPage<T> {

    String getHeadHtml();
    String getBodyText();
    String getBodyHtml();

    default String getTitle() {
        return waitWhileNotVisible($(".smallTitle")).getText();
    }

    default boolean containsObject(IdentifierObject identifier) {
        getBodyText();
        return findObject(identifier.getNodeCriteria(), $(By.tagName("body"))).is(Condition.visible);
    }

    default boolean containsText(String text) {
        return getBodyText().contains(text);
    }

    default T acceptAlertOnPage() {
        try {
            //switchTo().alert().accept();
        } catch (Exception ex) {

        }
        return getPage();
    }

    default T dismissAlertOnPage() {
        try {
            switchTo().alert().dismiss();
        } catch (Exception ex) {

        }
        return getPage();
    }
}
