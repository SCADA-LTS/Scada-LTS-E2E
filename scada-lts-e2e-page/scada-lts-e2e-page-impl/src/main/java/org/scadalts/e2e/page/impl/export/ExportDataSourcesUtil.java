package org.scadalts.e2e.page.impl.export;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;

import java.util.ArrayList;
import java.util.List;

import static org.scadalts.e2e.page.core.criterias.Tag.td;
import static org.scadalts.e2e.page.core.criterias.Tag.tr;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObjects;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.clazz;

@Log4j2
public class ExportDataSourcesUtil {

    public static List<UpdateDataSourceCriteria> dataSourcesToCriterias(By selectEnableAction, SelenideElement source) {

        NodeCriteria rowCriteria = NodeCriteria.every(tr(), clazz("row"));

        List<SelenideElement> rows = findObjects(rowCriteria,source);

        NodeCriteria nameCriteria = NodeCriteria.every(6, 1, td());
        NodeCriteria typeCriteria = NodeCriteria.every(6, 2, td());
        NodeCriteria enableCriteria = NodeCriteria.every(6, 4, td());

        List<UpdateDataSourceCriteria> criterias = new ArrayList<>();

        for (SelenideElement row: rows) {
            logger.info("element: {}", row);
            String nameText = findObject(nameCriteria,row).getText();
            String typeText = findObject(typeCriteria,row).getText();
            boolean enabledValue = findObject(enableCriteria, row).$(selectEnableAction).is(Condition.visible);
            logger.info("nameText: {}, typeText: {}, enabledValue: {}\n", nameText, typeText, enabledValue);
            UpdateDataSourceCriteria criteria = UpdateDataSourceCriteria.criteriaPeriodTypeAny(new DataSourceIdentifier(nameText,
                    DataSourceType.getType(typeText)),enabledValue);
            criterias.add(criteria);
        }
        return criterias;
    }
}
