package org.scadalts.e2e.page.impl.pages.datasource.datapoint;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.pages.PageObject;

import static com.codeborne.selenide.Condition.not;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;
import static org.scadalts.e2e.page.impl.util.SelectUtil.searchObject;
import static org.scadalts.e2e.page.impl.util.SelectUtil.selectObject;

public class SelectDataPointUtil {

    public static void selectPoint(SelenideElement source,
                                              IdentifierObject identifierObject,
                                              PageObject<?> pageObject) {
        waitWhile(selectObject(source, identifierObject, pageObject, Tag.li()),
                not(Condition.visible))
                .click();
    }

    public static void searchPoint(SelenideElement source, IdentifierObject identifierObject, PageObject<?> pageObject) {
        waitWhile(searchObject(source, By.cssSelector(".chosen-search input"), By.className("no-results"), identifierObject, pageObject, Tag.li()),
                not(Condition.visible))
                .click();
    }
}
