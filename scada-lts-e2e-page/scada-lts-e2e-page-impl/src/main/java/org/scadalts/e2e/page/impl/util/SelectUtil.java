package org.scadalts.e2e.page.impl.util;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.pages.PageObject;

import static com.codeborne.selenide.Condition.not;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

public class SelectUtil {


    public static SelenideElement selectObject(SelenideElement source,
                                               IdentifierObject identifierObject,
                                               PageObject<?> pageObject,
                                               Tag tag) {
        pageObject.delay();
        waitWhile(source, not(Condition.visible)).click();
        NodeCriteria nodeCriteria = NodeCriteria.exactlyTypeAny(identifierObject, tag);
        return waitWhile(findObject(nodeCriteria, source), not(Condition.visible));
    }

    public static SelenideElement searchObject(SelenideElement source, By fieldSearchInSource,
                                               By waitIfVisibleInSource,
                                               IdentifierObject identifierObject,
                                               PageObject<?> pageObject,
                                               Tag tag) {
        pageObject.delay();
        waitWhile(source, not(Condition.visible)).click();
        waitWhile(source.$(fieldSearchInSource), not(Condition.visible)).sendKeys(identifierObject.getValue());
        waitWhile(source.$(waitIfVisibleInSource), Condition.visible);
        NodeCriteria nodeCriteria = NodeCriteria.exactlyTypeAny(identifierObject, tag);
        return waitWhile(findObject(nodeCriteria, source), not(Condition.visible));
    }
}
