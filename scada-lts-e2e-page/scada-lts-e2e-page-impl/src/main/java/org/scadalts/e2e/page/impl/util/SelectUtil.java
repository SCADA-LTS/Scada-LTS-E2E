package org.scadalts.e2e.page.impl.util;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.pages.PageObject;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;

import static com.codeborne.selenide.Condition.not;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

public class SelectUtil {

    public static void selectPoint(SelenideElement chosen, DataSourcePointCriteria dataPointName, PageObject<?> pageObject) {
        pageObject.delay();
        waitWhile(chosen, not(Condition.visible)).click();
        NodeCriteria nodeCriteria = NodeCriteria.exactlyTypeAny(dataPointName.getIdentifier(), Tag.li());
        findObject(nodeCriteria, chosen).click();
    }
}
