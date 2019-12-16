package org.scadalts.e2e.pages.page.datasource;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.pages.util.E2eUtils;
import org.scadalts.e2e.pages.dict.DataSourceType;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.pages.util.E2eUtils.acceptAlert;
import static org.scadalts.e2e.pages.util.FindInTable.findRow;

public class DataSourcesPage extends MainPageObjectAbstract<DataSourcesPage> {

    @FindBy(id = "dataSourceTypes")
    private SelenideElement dataSourceTypes;

    @FindBy(css = "img[onclick='addDataSource()']")
    private SelenideElement addDataSource;

    @FindBy(css = "table .dataSourcesTable")
    private SelenideElement dataSourcesTable;

    private static final By SELECTOR_EDIT_DATA_SOURCE_BY = By.cssSelector("a[href*='data_source_edit.shtm?dsid=']");
    private static final By SELECTOR_DELETE_DATA_SOURCE_BY = By.cssSelector("img[title='Delete']");

    public DataSourcesPage(SelenideElement source) {
        super(source);
    }

    @Override
    public DataSourcesPage getPage() {
        return this;
    }

    public EditDataSourcePage openDataSourceCreator() {
        addDataSource.click();
        return page(EditDataSourcePage.class);
    }

    public EditDataSourceWithPointListPage openDataSourceEditor(String dataSourceName, DataSourceType dataSourceType) {
        _openDataSourceAction(dataSourceName, dataSourceType, SELECTOR_EDIT_DATA_SOURCE_BY);
        return page(EditDataSourceWithPointListPage.class);
    }

    public void deleteDataSource(String dataSourceName, DataSourceType dataSourceType) {
        _openDataSourceAction(dataSourceName, dataSourceType, SELECTOR_DELETE_DATA_SOURCE_BY);
        acceptAlert();
    }

    public String selectDataSourceType(DataSourceType dataSourceType) {
        dataSourceTypes.selectOption(dataSourceType.getTypeName());
        return dataSourceTypes.getValue();
    }

    private void _openDataSourceAction(String dataSourceName, DataSourceType dataSourceType, By selector) {
        SelenideElement rowWithActions = findRow(dataSourceName, dataSourceType, By.cssSelector(".row"), dataSourcesTable);
        rowWithActions.findElement(selector).click();
    }
}