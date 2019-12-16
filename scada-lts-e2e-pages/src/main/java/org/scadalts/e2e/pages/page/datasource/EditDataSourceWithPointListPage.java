package org.scadalts.e2e.pages.page.datasource;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.pages.util.E2eUtils;
import org.scadalts.e2e.pages.dict.DataPointType;
import org.scadalts.e2e.pages.page.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.pages.page.datasource.datapoint.PropertiesDataPointPage;

import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.pages.util.E2eUtils.acceptAlert;
import static org.scadalts.e2e.pages.util.FindInTable.findRow;


public class EditDataSourceWithPointListPage extends EditDataSourcePage {

    @FindBy(id = "dsStatusImg")
    private SelenideElement dataSourceOnOff;

    @FindBy(id = "enableAllImg")
    private SelenideElement enableAllDataPoint;

    @FindBy(id = "editImg-1")
    private SelenideElement addDataPoint;

    @FindBy(css = "tbody #pointsList")
    private SelenideElement dataPointsTable;


    private static final By SELECTOR_EDIT_DATA_POINT_BY = By.cssSelector("img[onclick*='editPoint(']");
    private static final By SELECTOR_PROPERTIES_DATA_POINT_BY = By.cssSelector("img[onclick*='data_point_edit.shtm?dpid=']");


    public void dataSourceOnOff() {
        dataSourceOnOff.click();
    }

    public void enableAllDataPoint() {
        enableAllDataPoint.click();
    }

    public EditDataPointPage addDataPoint() {
        addDataPoint.click();
        acceptAlert();
        return page(EditDataPointPage.class);
    }

    public EditDataPointPage openDataPointEditor(String dataPointName, DataPointType dataPointType) {
        _openDataSourceAction(dataPointName, dataPointType, SELECTOR_EDIT_DATA_POINT_BY);
        return page(EditDataPointPage.class);
    }

    public PropertiesDataPointPage openDataPointProperties(String dataPointName, DataPointType dataPointType) {
        _openDataSourceAction(dataPointName, dataPointType, SELECTOR_PROPERTIES_DATA_POINT_BY);
        return page(PropertiesDataPointPage.class);
    }

    private void _openDataSourceAction(String dataPointName, DataPointType dataPointType, By selectAction) {
        SelenideElement rowWithActions = findRow(dataPointName, dataPointType, By.tagName("tr"), dataPointsTable);
        rowWithActions.findElement(selectAction).click();
    }

}
