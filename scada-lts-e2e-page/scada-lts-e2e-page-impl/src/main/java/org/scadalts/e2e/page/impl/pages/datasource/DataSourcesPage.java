package org.scadalts.e2e.page.impl.pages.datasource;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criteria.ActionCriteria;
import org.scadalts.e2e.page.core.criteria.RowCriteria;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.DataSourceType;

import static org.scadalts.e2e.page.core.util.E2eUtil.acceptAlert;
import static org.scadalts.e2e.page.core.util.TableElementUtil.findActionByClassCss;

public class DataSourcesPage extends MainPageObjectAbstract<DataSourcesPage> {

    @FindBy(id = "dataSourceTypes")
    private SelenideElement dataSourceTypes;

    @FindBy(css = "img[onclick='addDataSource()']")
    private SelenideElement addDataSource;

    @FindBy(css = "table .dataSourcesTable")
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
        return Selenide.page(EditDataSourceWithPointListPage.class);
    }

    public DataSourcesPage deleteDataSource(DataSourceCriteria dataSourceParams) {
        _findAction(dataSourceParams,SELECTOR_ACTION_DELETE_DATA_SOURCE_BY).click();
        acceptAlert();
        return this;
    }

    public DataSourcesPage enableDataSource(DataSourceCriteria dataSourceParams) {
        _findAction(dataSourceParams,SELECTOR_ACTION_ENABLE_DATA_SOURCE_BY).click();
        acceptAlert();
        return this;
    }

    public DataSourcesPage disableDataSource(DataSourceCriteria dataSourceParams) {
        _findAction(dataSourceParams,SELECTOR_ACTION_DISABLE_DATA_SOURCE_BY).click();
        acceptAlert();
        return this;
    }

    public DataSourcesPage selectDataSourceTypeOnPage(DataSourceType dataSourceType) {
        dataSourceTypes.selectOption(dataSourceType.getTypeName());
        return this;
    }

    public EditDataSourcePage openDataSourceCreator(DataSourceType dataSourceType) {
        dataSourceTypes.selectOption(dataSourceType.getTypeName());
        return _openDataSourceCreator();
    }


    public String selectDataSourceType(DataSourceType dataSourceType) {
        dataSourceTypes.selectOption(dataSourceType.getTypeName());
        return dataSourceTypes.getValue();
    }

    private SelenideElement _findAction(DataSourceCriteria dataSourceParams, By selectAction) {
        RowCriteria rowCriteria = new RowCriteria(dataSourceParams.getName(),
                dataSourceParams.getType());
        ActionCriteria actionCriteria = new ActionCriteria(rowCriteria, selectAction);
        return findActionByClassCss(actionCriteria, "row", dataSourcesTable);
    }


    private EditDataSourcePage _openDataSourceCreator() {
        addDataSource.click();
        return Selenide.page(EditDataSourcePage.class);
    }
}