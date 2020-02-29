package org.scadalts.e2e.page.core.utils;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.scadalts.e2e.common.utils.ExecutorUtil;
import org.scadalts.e2e.page.core.criterias.ActionCriteria;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.exceptions.DynamicElementException;

import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.reloadElement;

@Log4j2
public abstract class DynamicElementUtil {

    public static SelenideElement findAction(CriteriaObject criteria, By selectAction, SelenideElement source) throws DynamicElementException {
        ActionCriteria actionCriteria = new ActionCriteria(criteria, selectAction);
        return ExecutorUtil.execute(DynamicElementUtil::_findAction, actionCriteria, source, DynamicElementException::new);
    }

    private static SelenideElement _findAction(ActionCriteria criteria, SelenideElement source) {
        SelenideElement reloadedElement = _prepareSource(criteria, source);
        SelenideElement objectWithActions = _findObjectWithActions(criteria, reloadedElement);
        return objectWithActions.$(criteria.getSelectAction());
    }

    private static SelenideElement _findObjectWithActions(ActionCriteria criteria, SelenideElement source) {
        String xpath = criteria.getCriteria().getXpath();
        logger.info("xpath: {}", xpath);
        return source.$(By.xpath(xpath));
    }

    private static SelenideElement _prepareSource(ActionCriteria criteria, SelenideElement source) {
        String text = source.getText();
        String objectName = criteria.getCriteria().getIdentifier().getValue();
        return text.contains(objectName) ? source : reloadElement(source);
    }
}
