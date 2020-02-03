package org.scadalts.e2e.page.core.util;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.criteria.ActionCriteria;
import org.scadalts.e2e.page.core.criteria.RowCriteria;

import static org.scadalts.e2e.page.core.util.StabilityUtil.reloadElement;

@Log4j2
public abstract class TableElementUtil {

    public static SelenideElement findActionByClassCss(ActionCriteria criteria, String classCss, SelenideElement table) {
        SelenideElement reloadedElement = _prepareTable(criteria, table);
        SelenideElement rowWithActions = findRowByClassCss(criteria.getRowCriteria(), classCss, reloadedElement);
        return rowWithActions.$(criteria.getSelectAction());
    }

    public static SelenideElement findAction(ActionCriteria criteria, SelenideElement table) {
        SelenideElement reloadedElement = _prepareTable(criteria, table);
        SelenideElement rowWithActions = findRow(criteria.getRowCriteria(), reloadedElement);
        return rowWithActions.$(criteria.getSelectAction());
    }

    static SelenideElement findRowByClassCss(RowCriteria criteria, String classCss, SelenideElement table) {
        String xpath = XpathFactory.xpathFindTrByClassCss(criteria, classCss);
        logger.debug("xpath: {}", xpath);
        return table.$(By.xpath(xpath));
    }


    static SelenideElement findRow(RowCriteria criteria, SelenideElement table) {
        String xpath = XpathFactory.xpathFindTr(criteria);
        logger.debug("xpath: {}", xpath);
        return table.$(By.xpath(xpath));
    }

    private static SelenideElement _prepareTable(ActionCriteria criteria, SelenideElement table) {
        String text = table.getText();
        String objectName = criteria.getRowCriteria().getName();
        return text.contains(objectName) ? table : reloadElement(table);
    }

}
