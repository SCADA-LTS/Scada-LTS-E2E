package org.scadalts.e2e.page.impl.pages.datasource;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.export.ExportDataSourcesUtil;

import java.util.List;

import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAlertAfterClick;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.*;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

@Log4j2
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

    public EditDataSourceWithPointListPage openDataSourceEditor(DataSourceIdentifier identifier) {
        _findAction(identifier, SELECTOR_ACTION_EDIT_DATA_SOURCE_BY).click();
        return page(EditDataSourceWithPointListPage.class);
    }

    public DataSourcesPage deleteDataSource(DataSourceIdentifier identifier) {
        acceptAlertAfterClick(_findAction(identifier,SELECTOR_ACTION_DELETE_DATA_SOURCE_BY));
        waitWhile(_findObject(identifier), Condition.visible);
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
        logger.info("disabled actions number: {}", disableActions.size());
        for (SelenideElement disableAction: disableActions) {
            delay();
            logger.info("disableAction: {}", disableAction);
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

    public List<DataSourceCriteria> getDataSources() {
        return ExportDataSourcesUtil.dataSourcesEnabledToCriterias(SELECTOR_ACTION_DISABLE_DATA_SOURCE_BY,dataSourcesTable);
    }

    public DataSourcesPage enableDataSource(DataSourceIdentifier identifier) {
        SelenideElement selenideElement = _findAction(identifier,SELECTOR_ACTION_ENABLE_DATA_SOURCE_BY);
        acceptAlertAfterClick(selenideElement);
        return this;
    }

    public boolean isEnabledDataSource(DataSourceIdentifier identifier) {
        SelenideElement selenideElement = _findObject(identifier).$(SELECTOR_ACTION_DISABLE_DATA_SOURCE_BY);
        return selenideElement.is(Condition.visible);
    }

    public DataSourcesPage disableDataSource(DataSourceIdentifier identifier) {
        SelenideElement selenideElement = _findAction(identifier,SELECTOR_ACTION_DISABLE_DATA_SOURCE_BY);
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

    private SelenideElement _findAction(DataSourceIdentifier identifier, By selectAction) {
        delay();
        return findAction(identifier.getNodeCriteria(), selectAction, dataSourcesTable);
    }

    private SelenideElement _findObject(DataSourceIdentifier identifier) {
        delay();
        return findObject(identifier.getNodeCriteria(), dataSourcesTable);
    }

    private List<SelenideElement> _findObjects(DataSourceIdentifier identifier) {
        delay();
        return findObjects(identifier.getNodeCriteria(), dataSourcesTable);
    }

    private List<SelenideElement> _findActions(DataSourceIdentifier identifier, By selectAction) {
        return findActions(identifier.getNodeCriteria(),selectAction,dataSourcesTable);
    }

    private EditDataSourcePage _openDataSourceCreator() {
        delay();
        addDataSource.click();
        return page(EditDataSourcePage.class);
    }
}