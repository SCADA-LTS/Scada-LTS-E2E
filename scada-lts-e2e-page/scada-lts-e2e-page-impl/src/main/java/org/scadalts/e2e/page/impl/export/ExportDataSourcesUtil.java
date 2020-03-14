package org.scadalts.e2e.page.impl.export;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.criterias.CssClass;
import org.scadalts.e2e.page.core.criterias.NodeCriteria;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;

import java.util.ArrayList;
import java.util.List;

import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObjects;

@Log4j2
public class ExportDataSourcesUtil {

    public static List<DataSourceCriteria> dataSourcesTableToCriterias(By selectEnableAction, SelenideElement source) {
        NodeCriteria rowCriteria = NodeCriteria.every(1, 0, Tag.tr(), new CssClass("row"));

        List<SelenideElement> rows = findObjects(rowCriteria,source);

        NodeCriteria nameCriteria = NodeCriteria.every(6, 1,Tag.td());
        NodeCriteria typeCriteria = NodeCriteria.every(6, 2,Tag.td());
        NodeCriteria enabledCriteria = NodeCriteria.every(6, 4,Tag.td());

        List<DataSourceCriteria> criterias = new ArrayList<>();
        for (SelenideElement row: rows) {
            logger.info("element: {}", row);
            String nameText = findObject(nameCriteria,row).getText();
            String typeText = findObject(typeCriteria,row).getText();
            boolean enabledValue = findObject(enabledCriteria, row).$(selectEnableAction).is(Condition.visible);
            logger.info("nameText: {}, typeText: {}, enabledValue: {}", nameText, typeText, enabledValue);
            DataSourceCriteria criteria = DataSourceCriteria.criteriaPeriodTypeAny(new DataSourceIdentifier(nameText),DataSourceType.getType(typeText),enabledValue);
            criterias.add(criteria);
        }
        return criterias;
    }
}
