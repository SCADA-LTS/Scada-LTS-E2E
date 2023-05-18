package org.scadalts.e2e.page.impl.pages.datasource;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.export.ExportDataSourcesUtil;

import java.util.List;

import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAfterClick;
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

    @FindBy(css = "a[href='data_sources.shtm']")
    private SelenideElement source;

    private static final By SELECTOR_ACTION_EDIT_DATA_SOURCE_BY = By.cssSelector("a[href*='data_source_edit.shtm?dsid=']");
    private static final By SELECTOR_ACTION_DELETE_DATA_SOURCE_BY = By.cssSelector("img[title='Delete']");
    private static final By SELECTOR_ACTION_ENABLE_DATA_SOURCE_BY = By.cssSelector("img[src='images/database_stop.png']");
    private static final By SELECTOR_ACTION_DISABLE_DATA_SOURCE_BY = By.cssSelector("img[src='images/database_go.png']");

    public static final String TITLE = "Data sources";

    public DataSourcesPage() {
        super(TITLE, "/data_sources.shtm");
    }

    @Override
    public DataSourcesPage getPage() {
        return this;
    }

    public EditDataSourceWithPointListPage openDataSourceEditor(DataSourceIdentifier identifier) {
        _findAction(identifier, SELECTOR_ACTION_EDIT_DATA_SOURCE_BY).click();
        printCurrentUrl();
        return page(EditDataSourceWithPointListPage.class);
    }

    public DataSourcesPage deleteDataSource(DataSourceIdentifier identifier) {
        acceptAfterClick(_findAction(identifier,SELECTOR_ACTION_DELETE_DATA_SOURCE_BY));
        waitWhile(_findObject(identifier), Condition.visible);
        return this;
    }

    public EditDataSourceWithPointListPage openDataSourceEditor(DataSourceCriteria criteria) {
        return openDataSourceEditor(criteria.getIdentifier());
    }

    public DataSourcesPage deleteDataSource(DataSourceCriteria criteria) {
        return deleteDataSource(criteria.getIdentifier());
    }

    public DataSourcesPage deleteAllDataSourcesMatching(NodeCriteria nodeCriteria) {
        waitWhile(dataSourcesTable, Condition.empty);
        List<SelenideElement> deleteActions = findActions(nodeCriteria,SELECTOR_ACTION_DELETE_DATA_SOURCE_BY,dataSourcesTable);
        for (SelenideElement deleteAction: deleteActions) {
            delay();
            acceptAfterClick(deleteAction);
            waitWhile(deleteAction, Condition.visible);
            logger.info("deleted: {}", deleteAction);
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
            acceptAfterClick(disableAction);
            waitWhile(disableAction, Condition.visible);
        }
        return this;
    }

    public DataSourcesPage enableAllDataSourcesMatching(NodeCriteria nodeCriteria) {
        waitWhile(dataSourcesTable, Condition.empty);
        List<SelenideElement> enableActions = findActions(nodeCriteria,SELECTOR_ACTION_ENABLE_DATA_SOURCE_BY,dataSourcesTable);
        for (SelenideElement enableAction: enableActions) {
            delay();
            acceptAfterClick(enableAction);
            waitWhile(enableAction, Condition.visible);
        }
        return this;
    }

    public List<DataSourceCriteria> getDataSources() {
        return ExportDataSourcesUtil.dataSourcesToCriterias(SELECTOR_ACTION_DISABLE_DATA_SOURCE_BY,dataSourcesTable);
    }

    public DataSourcesPage enableDataSource(DataSourceIdentifier identifier) {
        SelenideElement selenideElement = _findAction(identifier,SELECTOR_ACTION_ENABLE_DATA_SOURCE_BY);
        acceptAfterClick(selenideElement);
        return this;
    }

    public boolean isEnabledDataSource(DataSourceIdentifier identifier) {
        SelenideElement selenideElement = _findObject(identifier).$(SELECTOR_ACTION_DISABLE_DATA_SOURCE_BY);
        return selenideElement.is(Condition.visible);
    }

    public DataSourcesPage disableDataSource(DataSourceIdentifier identifier) {
        SelenideElement selenideElement = _findAction(identifier,SELECTOR_ACTION_DISABLE_DATA_SOURCE_BY);
        acceptAfterClick(selenideElement);
        return this;
    }

    public DataSourcesPage enableDataSource(DataSourceCriteria criteria) {
        return enableDataSource(criteria.getIdentifier());
    }

    public boolean isEnabledDataSource(DataSourceCriteria criteria) {
        return isEnabledDataSource(criteria.getIdentifier());
    }

    public DataSourcesPage disableDataSource(DataSourceCriteria criteria) {
        return disableDataSource(criteria.getIdentifier());
    }

    public DataSourcesPage selectDataSourceTypeOnPage(DataSourceType dataSourceType) {
        delay();
        dataSourceTypes.selectOption(dataSourceType.getName());
        return this;
    }

    public EditDataSourcePage openDataSourceCreator(DataSourceType dataSourceType) {
        delay();
        dataSourceTypes.selectOption(dataSourceType.getName());
        return _openDataSourceCreator();
    }


    public String selectDataSourceType(DataSourceType dataSourceType) {
        delay();
        dataSourceTypes.selectOption(dataSourceType.getName());
        return dataSourceTypes.getValue();
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
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
        printCurrentUrl();
        return page(EditDataSourcePage.class);
    }
}