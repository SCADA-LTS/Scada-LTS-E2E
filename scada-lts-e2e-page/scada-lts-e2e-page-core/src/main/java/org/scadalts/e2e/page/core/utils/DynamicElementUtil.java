package org.scadalts.e2e.page.core.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.scadalts.e2e.common.utils.ExecutorUtil;
import org.scadalts.e2e.page.core.criterias.ActionCriteria;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.RowCriteria;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.exceptions.DynamicElementException;

import static com.codeborne.selenide.Condition.not;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

@Log4j2
public abstract class DynamicElementUtil {

    public static SelenideElement findAction(RowCriteria criteria, By selectAction, SelenideElement source) throws DynamicElementException {
        ActionCriteria actionCriteria = new ActionCriteria(criteria, selectAction);
        return ExecutorUtil.execute(DynamicElementUtil::_findAction, actionCriteria, source, DynamicElementException::new);
    }

    public static SelenideElement findObject(RowCriteria criteria, SelenideElement source) throws DynamicElementException {
        return ExecutorUtil.execute(DynamicElementUtil::_findAction, criteria, source, DynamicElementException::new);
    }

    public static ElementsCollection findObjects(SelenideElement source) throws DynamicElementException {
        String xpath = XpathFactory.xpathEveryXElementFirst(3, Tag.td());
        logger.info("xpath: {}", xpath);

        ElementsCollection elements = waitWhile(source, not(Condition.visible))
                .$$(By.xpath(xpath));
        logger.debug("elements: {}", elements);
        return elements;
    }

    private static SelenideElement _findAction(RowCriteria criteria, SelenideElement source) {
        SelenideElement reloadedElement = _prepareSource(criteria, source);
        return _findObjectWithActions(criteria, reloadedElement);
    }

    private static SelenideElement _findAction(ActionCriteria criteria, SelenideElement source) {
        SelenideElement reloadedElement = _prepareSource(criteria.getCriteria(), source);
        SelenideElement objectWithActions = _findObjectWithActions(criteria, reloadedElement);
        return objectWithActions.$(criteria.getSelectAction());
    }

    private static SelenideElement _findObjectWithActions(ActionCriteria criteria, SelenideElement source) {
        return _findObjectWithActions(criteria.getCriteria(), source);
    }

    private static SelenideElement _findObjectWithActions(RowCriteria criteria, SelenideElement source) {
        String xpath = criteria.getXpath();
        logger.info("xpath: {}", xpath);
        return source.$(By.xpath(xpath));
    }

    private static SelenideElement _prepareSource(CriteriaObject criteria, SelenideElement source) {
        String text = source.getText();
        String objectName = criteria.getIdentifier().getValue();
        return text.contains(objectName) ? source : waitWhile(source, not(Condition.visible));
    }
}
