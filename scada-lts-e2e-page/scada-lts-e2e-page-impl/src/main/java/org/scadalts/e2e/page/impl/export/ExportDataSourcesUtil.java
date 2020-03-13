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

    public static List<DataSourceCriteria> dataSourcesTable(By selectAction, SelenideElement source) {
        NodeCriteria trNode = NodeCriteria.every(1, 0, Tag.tr(), new CssClass("row"));

        List<SelenideElement> trObjects = findObjects(trNode,source);
        List<DataSourceCriteria> criterias = new ArrayList<>();
        for (SelenideElement tr: trObjects) {
            logger.info("element: {}", tr);
            NodeCriteria name = NodeCriteria.every(6, 1,Tag.td());
            String nameText = findObject(name,tr).getText();
            NodeCriteria type = NodeCriteria.every(6, 2,Tag.td());
            String typeText = findObject(type,tr).getText();
            NodeCriteria enabled = NodeCriteria.every(6, 4,Tag.td());

            boolean enabledValue = findObject(enabled, tr).$(selectAction).is(Condition.visible);
            logger.info("nameText: {}, typeText: {}, enabledValue: {}", nameText, typeText, enabledValue);
            DataSourceCriteria criteria = DataSourceCriteria.criteria(new DataSourceIdentifier(nameText),DataSourceType.getType(typeText),enabledValue);
            criterias.add(criteria);
        }
        return criterias;
    }
}
