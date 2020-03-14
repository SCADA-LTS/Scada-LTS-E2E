package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.NodeCriteria;
import org.scadalts.e2e.page.core.criterias.Tag;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

interface Waitable<T extends PageObject<T>> extends GetPage<T> {

    default T waitOnPage(long wait) {
        Selenide.sleep(wait);
        return getPage();
    }

    default T waitForObject(NodeCriteria nodeCriteria) {
        waitWhile($(By.xpath(nodeCriteria.getXpath())), not(Condition.visible));
        return getPage();
    }

    default T waitForObject(CriteriaObject criteriaObject) {
        NodeCriteria nodeCriteria = NodeCriteria.exactly(criteriaObject.getIdentifier(),
                criteriaObject.getType(), Tag.each());
        return waitForObject(nodeCriteria);
    }

    default T waitForObject(String id) {
        waitWhile($(By.id(id)), not(Condition.visible));
        return getPage();
    }
}
