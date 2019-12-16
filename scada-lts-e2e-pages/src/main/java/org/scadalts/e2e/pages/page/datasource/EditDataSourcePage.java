package org.scadalts.e2e.pages.page.datasource;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.pages.dict.UpdatePeriodType;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

import static com.codeborne.selenide.Selenide.page;
import static javax.xml.bind.DatatypeConverter.parseInt;

public class EditDataSourcePage extends PageObjectAbstract {

    @FindBy(id = "dataSourceName")
    private SelenideElement dataSourceName;

    @FindBy(id = "dataSourceXid")
    private SelenideElement dataSourceXid;

    @FindBy(id = "updatePeriods")
    private SelenideElement updatePeriods;

    @FindBy(id = "updatePeriodType")
    private SelenideElement updatePeriodType;

    @FindBy(css = "img[onclick='saveDataSource()']")
    private SelenideElement saveDataSource;

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName.clear();
        this.dataSourceName.sendKeys(dataSourceName);
    }

    public void setDataSourceXid(String dataSourceXid) {
        this.dataSourceXid.clear();
        this.dataSourceXid.sendKeys(dataSourceXid);
    }

    public void setUpdatePeriods(int updatePeriods) {
        this.updatePeriods.clear();
        this.updatePeriods.sendKeys(String.valueOf(updatePeriods));
    }

    public String selectUpdatePeriodType(UpdatePeriodType componentName) {
        updatePeriodType.selectOption(componentName.getTypeName());
        return updatePeriodType.getValue();
    }

    public EditDataSourceWithPointListPage saveSaveDataSource() {
        saveDataSource.click();
        return page(new EditDataSourceWithPointListPage());
    }

    public String getDataSourceName() {
        return dataSourceName.getValue();
    }

    public String getDataSourceXid() {
        return dataSourceXid.getValue();
    }

    public int getUpdatePeriods() {
        return parseInt(updatePeriods.getValue());
    }

    public UpdatePeriodType getUpdatePeriodType() {
        return UpdatePeriodType.getType(updatePeriodType.getSelectedText());
    }

}
