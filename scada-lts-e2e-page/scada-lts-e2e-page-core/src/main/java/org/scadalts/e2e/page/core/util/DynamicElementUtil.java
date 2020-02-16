package org.scadalts.e2e.page.core.util;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.scadalts.e2e.common.utils.ExecutorUtil;
import org.scadalts.e2e.page.core.criteria.ActionCriteria;
import org.scadalts.e2e.page.core.criteria.RowCriteria;
import org.scadalts.e2e.page.core.exceptions.DynamicElementException;

import static org.scadalts.e2e.page.core.util.StabilityUtil.reloadElement;

@Log4j2
public abstract class DynamicElementUtil {

    public static SelenideElement findActionByClassCss(ActionCriteria criteria, String classCss, SelenideElement source) throws DynamicElementException {
        return ExecutorUtil.execute(DynamicElementUtil::_findActionByClassCss, criteria, classCss,
                source, DynamicElementException::new);
    }

    public static SelenideElement findAction(ActionCriteria criteria, SelenideElement source) throws DynamicElementException {
        return ExecutorUtil.execute(DynamicElementUtil::_findAction, criteria, source, DynamicElementException::new);
    }

    public static SelenideElement findAction(ActionCriteria criteria, SelenideElement source, String tagTarget) throws DynamicElementException {
        return ExecutorUtil.execute(DynamicElementUtil::_findAction, criteria, source, tagTarget,
                DynamicElementException::new);

    }

    private static SelenideElement _findActionByClassCss(ActionCriteria criteria, String classCss, SelenideElement source) {
        SelenideElement reloadedElement = _prepareSource(criteria, source);
        SelenideElement rowWithActions = _findRowByClassCss(criteria.getRowCriteria(), classCss, reloadedElement);
        return rowWithActions.$(criteria.getSelectAction());
    }

    private static SelenideElement _findAction(ActionCriteria criteria, SelenideElement source) {
        SelenideElement reloadedElement = _prepareSource(criteria, source);
        SelenideElement rowWithActions = _findRow(criteria.getRowCriteria(), reloadedElement);
        return rowWithActions.$(criteria.getSelectAction());
    }

    private static SelenideElement _findAction(ActionCriteria criteria, SelenideElement source, String tagTarget) {
        SelenideElement reloadedElement = _prepareSource(criteria, source);
        SelenideElement rowWithActions = _find(criteria.getRowCriteria(), reloadedElement, tagTarget);
        return rowWithActions.$(criteria.getSelectAction());
    }

    private static SelenideElement _find(RowCriteria criteria, SelenideElement source, String tagTarget) {
        String xpath = XpathFactory.xpathFind(criteria, tagTarget);
        logger.info("xpath: {}", xpath);
        return source.$(By.xpath(xpath));
    }

    private static SelenideElement _findRowByClassCss(RowCriteria criteria, String classCss, SelenideElement source) {
        String xpath = XpathFactory.xpathFindTrByClassCss(criteria, classCss);
        logger.info("xpath: {}", xpath);
        return source.$(By.xpath(xpath));
    }


    private static SelenideElement _findRow(RowCriteria criteria, SelenideElement source) {
        String xpath = XpathFactory.xpathFindTr(criteria);
        logger.info("xpath: {}", xpath);
        return source.$(By.xpath(xpath));
    }

    private static SelenideElement _prepareSource(ActionCriteria criteria, SelenideElement source) {
        String text = source.getText();
        String objectName = criteria.getRowCriteria().getIdentifier();
        return text.contains(objectName) ? source : reloadElement(source);
    }

}
