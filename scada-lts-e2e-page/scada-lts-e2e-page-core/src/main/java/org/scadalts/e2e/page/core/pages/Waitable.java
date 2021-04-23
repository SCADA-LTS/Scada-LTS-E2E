package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

interface Waitable<T extends PageObject<T>> extends GetPage<T> {

    Logger logger = LogManager.getLogger(Waitable.class);

    default T waitOnPage(long wait) {
        Selenide.sleep(wait);
        return getPage();
    }

    default T waitForObject(NodeCriteria nodeCriteria) {
        logger.info("xpath: {}, in: {}", nodeCriteria.getXpath(), ".");
        waitWhile($(By.xpath(nodeCriteria.getXpath())), not(Condition.visible));
        return getPage();
    }

    default T waitForObject(IdentifierObject identifier) {
        NodeCriteria nodeCriteria = NodeCriteria.exactly(identifier, Tag.each());
        return waitForObject(nodeCriteria);
    }

    default T waitForObject(String id) {
        logger.info("id: {}, in: {}", id, ".");
        waitWhile($(By.id(id)), not(Condition.visible));
        return getPage();
    }
}
