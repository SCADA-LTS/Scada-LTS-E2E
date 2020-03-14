package org.scadalts.e2e.page.impl.pages.datasource;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.CssClass;
import org.scadalts.e2e.page.core.criterias.NodeCriteria;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.export.ExportDataSourcesUtil;

import java.util.List;

import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAlertAfterClick;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.*;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

public class DataSourcesPage extends MainPageObjectAbstract<DataSourcesPage> {

    @FindBy(id = "dataSourceTypes")
    private SelenideElement dataSourceTypes;

    @FindBy(css = "img[onclick='addDataSource()']")
    private SelenideElement addDataSource;

    @FindBy(css = "table table .dataSourcesTable")
    private SelenideElement dataSourcesTable;

    private static final By SELECTOR_ACTION_EDIT_DATA_SOURCE_BY = By.cssSelector("a[href*='data_source_edit.shtm?dsid=']");
    private static final By SELECTOR_ACTION_DELETE_DATA_SOURCE_BY = By.cssSelector("img[title='Delete']");
    private static final By SELECTOR_ACTION_ENABLE_DATA_SOURCE_BY = By.cssSelector("img[src='images/database_stop.png']");
    private static final By SELECTOR_ACTION_DISABLE_DATA_SOURCE_BY = By.cssSelector("img[src='images/database_go.png']");

    public static final String TITLE = "Data sources";

    public DataSourcesPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public DataSourcesPage getPage() {
        return this;
    }

    public EditDataSourceWithPointListPage openDataSourceEditor(DataSourceCriteria dataSourceParams) {
        _findAction(dataSourceParams, SELECTOR_ACTION_EDIT_DATA_SOURCE_BY).click();
        return page(EditDataSourceWithPointListPage.class);
    }

    public DataSourcesPage deleteDataSource(DataSourceCriteria dataSourceCriteria) {
        acceptAlertAfterClick(_findAction(dataSourceCriteria,SELECTOR_ACTION_DELETE_DATA_SOURCE_BY));
        waitWhile(_findObject(dataSourceCriteria), Condition.visible);
        return this;
    }

    public DataSourcesPage deleteAllDataSourcesMatching(NodeCriteria nodeCriteria) {
        waitWhile(dataSourcesTable, Condition.empty);
        List<SelenideElement> deleteActions = findActions(nodeCriteria,SELECTOR_ACTION_DELETE_DATA_SOURCE_BY,dataSourcesTable);
        for (SelenideElement deleteAction: deleteActions) {
            delay();
            acceptAlertAfterClick(deleteAction);
            waitWhile(deleteAction, Condition.visible);
        }
        return this;
    }

    public DataSourcesPage disableAllDataSourcesMatching(NodeCriteria nodeCriteria) {
        waitWhile(dataSourcesTable, Condition.empty);
        List<SelenideElement> disableActions = findActions(nodeCriteria,SELECTOR_ACTION_DISABLE_DATA_SOURCE_BY,dataSourcesTable);
        for (SelenideElement disableAction: disableActions) {
            delay();
            acceptAlertAfterClick(disableAction);
            waitWhile(disableAction, Condition.visible);
        }
        return this;
    }

    public DataSourcesPage enableAllDataSourcesMatching(NodeCriteria nodeCriteria) {
        waitWhile(dataSourcesTable, Condition.empty);
        List<SelenideElement> enableActions = findActions(nodeCriteria,SELECTOR_ACTION_ENABLE_DATA_SOURCE_BY,dataSourcesTable);
        for (SelenideElement enableAction: enableActions) {
            delay();
            acceptAlertAfterClick(enableAction);
            waitWhile(enableAction, Condition.visible);
        }
        return this;
    }

    public List<DataSourceCriteria> dataSourcesTable() {
        return ExportDataSourcesUtil.dataSourcesTableToCriterias(SELECTOR_ACTION_DISABLE_DATA_SOURCE_BY,dataSourcesTable);
    }

    public DataSourcesPage enableDataSource(DataSourceCriteria dataSourceCriteria) {
        SelenideElement selenideElement = _findAction(dataSourceCriteria,SELECTOR_ACTION_ENABLE_DATA_SOURCE_BY);
        acceptAlertAfterClick(selenideElement);
        return this;
    }

    public boolean isEnabledDataSource(DataSourceCriteria dataSourceCriteria) {
        SelenideElement selenideElement = _findObject(dataSourceCriteria).$(SELECTOR_ACTION_DISABLE_DATA_SOURCE_BY);
        return selenideElement.is(Condition.visible);
    }

    public DataSourcesPage disableDataSource(DataSourceCriteria dataSourceCriteria) {
        SelenideElement selenideElement = _findAction(dataSourceCriteria,SELECTOR_ACTION_DISABLE_DATA_SOURCE_BY);
        acceptAlertAfterClick(selenideElement);
        return this;
    }

    public DataSourcesPage selectDataSourceTypeOnPage(DataSourceType dataSourceType) {
        delay();
        dataSourceTypes.selectOption(dataSourceType.getName());
        return this;
    }

    public EditDataSourcePage openDataSourceCreator(DataSourceType dataSourceType) {
        dataSourceTypes.selectOption(dataSourceType.getName());
        return _openDataSourceCreator();
    }


    public String selectDataSourceType(DataSourceType dataSourceType) {
        delay();
        dataSourceTypes.selectOption(dataSourceType.getName());
        return dataSourceTypes.getValue();
    }

    private SelenideElement _findAction(DataSourceCriteria criteria, By selectAction) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(criteria.getIdentifier(), criteria.getType(), Tag.tr(), new CssClass("row"));
        return findAction(nodeCriteria, selectAction, dataSourcesTable);
    }

    private SelenideElement _findObject(DataSourceCriteria criteria) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(criteria.getIdentifier(), criteria.getType(), Tag.tr(), new CssClass("row"));
        return findObject(nodeCriteria, dataSourcesTable);
    }

    private ElementsCollection _findObjects(DataSourceCriteria criteria) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(criteria.getIdentifier(), criteria.getType(), Tag.tr(), new CssClass("row"));
        return findObjects(nodeCriteria, dataSourcesTable);
    }

    private List<SelenideElement> _findActions(DataSourceCriteria dataSourceCriteria, By selectAction) {
        NodeCriteria nodeCriteria = NodeCriteria.exactly(dataSourceCriteria.getIdentifier(), dataSourceCriteria.getType(), Tag.tr(), new CssClass("row"));
        return findActions(nodeCriteria,selectAction,dataSourcesTable);
    }

    private EditDataSourcePage _openDataSourceCreator() {
        delay();
        addDataSource.click();
        return page(EditDataSourcePage.class);
    }
}