package org.scadalts.e2e.pages.page.datasource.datapoint;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.pages.util.E2eUtils;
import org.scadalts.e2e.pages.dict.ChangeType;
import org.scadalts.e2e.pages.dict.DataPointType;
import org.scadalts.e2e.pages.page.PageObjectAbstract;
import org.scadalts.e2e.pages.page.datasource.EditDataSourceWithPointListPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static javax.xml.bind.DatatypeConverter.parseBoolean;

public class EditDataPointPage extends PageObjectAbstract {

    @FindBy(id = "name")
    private SelenideElement dataPointName;

    @FindBy(id = "xid")
    private SelenideElement dataPointXid;

    @FindBy(id = "settable")
    private SelenideElement settableCheckbox;

    @FindBy(id = "dataTypeId")
    private SelenideElement dataTypes;

    @FindBy(id = "changeTypeId")
    private SelenideElement changeTypes;

    @FindBy(css = "img[onclick='savePoint()']")
    private SelenideElement saveDataPoint;

    @FindBy(css = "img[onclick='deletePoint()']")
    private SelenideElement deleteDataPoint;

    public void setDataPointName(String dataPointName) {
        this.dataPointName.clear();
        this.dataPointName.sendKeys(dataPointName);
    }

    public void setDataPointXid(String dataPointXid) {
        this.dataPointXid.clear();
        this.dataPointXid.sendKeys(dataPointXid);
    }

    public void enableSettable() {
        //this.settableCheckbox.clear();
        this.settableCheckbox.click();
    }

    public void disableSettable() {
        this.settableCheckbox.clear();
    }

    public String selectDataPointType(DataPointType dataPointType) {
        dataTypes.selectOption(dataPointType.getTypeName());
        E2eUtils.acceptAlert();
        return dataTypes.getValue();
    }

    public String selectChangeType(ChangeType changeType) {
        changeTypes.selectOption(changeType.getTypeName());
        return changeTypes.getValue();
    }
/*
    public <T> T select(DataPointType dataPointType, ChangeType changeType, Class<T> clazz) {
        selectDataPointType(dataPointType);
        selectChangeType(changeType);
        return page(clazz);
    }*/

    public void setStartValue(String startValue) {
        SelenideElement selenideElement = $("td *[id*='.startValue'");
        selenideElement.sendKeys(startValue);
    }

    public EditDataPointPage saveDataPoint() {
        saveDataPoint.click();
        E2eUtils.acceptAlert();
        return this;
    }

    public EditDataSourceWithPointListPage deleteDataPoint() {
        deleteDataPoint.click();
        E2eUtils.acceptAlert();
        return page(EditDataSourceWithPointListPage.class);
    }

    public String getDataPointName() {
        return dataPointName.getValue();
    }

    public String getDataPointXid() {
        return dataPointXid.getValue();
    }

    public boolean getSettableCheckbox() {
        return parseBoolean(settableCheckbox.getValue());
    }

    public DataPointType getDataTypes() {
        return DataPointType.getType(dataTypes.getSelectedText());
    }

    public ChangeType getChangeTypes() {
        return ChangeType.getType(changeTypes.getSelectedText());
    }
}
