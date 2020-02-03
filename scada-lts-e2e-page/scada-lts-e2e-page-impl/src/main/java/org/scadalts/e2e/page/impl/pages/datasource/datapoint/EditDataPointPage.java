package org.scadalts.e2e.page.impl.pages.datasource.datapoint;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourcePage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static javax.xml.bind.DatatypeConverter.parseBoolean;
import static org.scadalts.e2e.page.core.util.E2eUtil.acceptAlert;

public class EditDataPointPage extends PageObjectAbstract<EditDataPointPage> {

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

    private EditDataSourceWithPointListPage editDataSourceWithPointListPage;

    public static final String TITLE = "Point details";

    public EditDataPointPage() {
        super(TITLE);
        editDataSourceWithPointListPage = page(EditDataSourceWithPointListPage.class);
    }

    @Override
    public EditDataPointPage getPage() {
        return this;
    }

    public EditDataPointPage setDataPointName(String dataPointName) {
        this.dataPointName.clear();
        this.dataPointName.sendKeys(dataPointName);
        return this;
    }

    public EditDataPointPage setDataPointXid(String dataPointXid) {
        this.dataPointXid.clear();
        this.dataPointXid.sendKeys(dataPointXid);
        return this;
    }

    public EditDataPointPage enableSettable() {
        this.settableCheckbox.click();
        return this;
    }

    public EditDataPointPage disableSettable() {
        this.settableCheckbox.clear();
        return this;
    }

    public EditDataPointPage selectDataPointType(DataPointType dataPointType) {
        dataTypes.selectOption(dataPointType.getTypeName());
        acceptAlert();
        return this;
    }

    public EditDataPointPage selectChangeType(ChangeType changeType) {
        changeTypes.selectOption(changeType.getTypeName());
        return this;
    }

    public String selectDataPointTypeValue(DataPointType dataPointType) {
        dataTypes.selectOption(dataPointType.getTypeName());
        acceptAlert();
        return dataTypes.getValue();
    }

    public String selectChangeTypeValue(ChangeType changeType) {
        changeTypes.selectOption(changeType.getTypeName());
        return changeTypes.getValue();
    }

    public EditDataPointPage setStartValue(String startValue) {
        SelenideElement selenideElement = $("td *[id*='.startValue'");
        selenideElement.sendKeys(startValue);
        return this;
    }

    public EditDataPointPage saveDataPoint() {
        saveDataPoint.click();
        acceptAlert();
        return this;
    }

    public EditDataSourceWithPointListPage deleteDataPoint() {
        deleteDataPoint.click();
        acceptAlert();
        return page(EditDataSourceWithPointListPage.class);
    }

    public String getDataPointName() {
        return dataPointName.getValue();
    }

    public String getDataPointXid() {
        return dataPointXid.getValue();
    }

    public boolean isSettable() {
        return parseBoolean(settableCheckbox.getValue());
    }

    public DataPointType getDataTypes() {
        return DataPointType.getType(dataTypes.getSelectedText());
    }

    public ChangeType getChangeTypes() {
        return ChangeType.getType(changeTypes.getSelectedText());
    }

    public EditDataSourceWithPointListPage dataSourceOnOff() {
        return editDataSourceWithPointListPage.dataSourceOnOff();
    }

    public EditDataSourceWithPointListPage enableAllDataPoint() {
        return editDataSourceWithPointListPage.enableAllDataPoint();
    }

    public EditDataPointPage addDataPoint() {
        return editDataSourceWithPointListPage.addDataPoint();
    }

    public EditDataPointPage openDataPointEditor(DataPointCriteria criteria) {
        return editDataSourceWithPointListPage.openDataPointEditor(criteria);
    }

    public PropertiesDataPointPage openDataPointProperties(DataPointCriteria criteria) {
        return editDataSourceWithPointListPage.openDataPointProperties(criteria);
    }

    public EditDataSourceWithPointListPage enableDataPoint(DataPointCriteria criteria) {
        return editDataSourceWithPointListPage.enableDataPoint(criteria);
    }

    public EditDataSourceWithPointListPage disableDataPoint(DataPointCriteria criteria) {
        return editDataSourceWithPointListPage.disableDataPoint(criteria);
    }

    public EditDataSourcePage setDataSourceName(String dataSourceName) {
        return editDataSourceWithPointListPage.setDataSourceName(dataSourceName);
    }

    public EditDataSourcePage setDataSourceXid(String dataSourceXid) {
        return editDataSourceWithPointListPage.setDataSourceXid(dataSourceXid);
    }

    public EditDataSourcePage setUpdatePeriods(int updatePeriods) {
        return editDataSourceWithPointListPage.setUpdatePeriods(updatePeriods);
    }

    public EditDataSourcePage selectUpdatePeriodType(UpdatePeriodType componentName) {
        return editDataSourceWithPointListPage.selectUpdatePeriodType(componentName);
    }

    public String selectUpdatePeriodTypeValue(UpdatePeriodType componentName) {
        return editDataSourceWithPointListPage.selectUpdatePeriodTypeValue(componentName);
    }

    public EditDataSourceWithPointListPage saveDataSource() {
        return editDataSourceWithPointListPage.saveDataSource();
    }

    public String getDataSourceName() {
        return editDataSourceWithPointListPage.getDataSourceName();
    }

    public String getDataSourceXid() {
        return editDataSourceWithPointListPage.getDataSourceXid();
    }

    public int getUpdatePeriods() {
        return editDataSourceWithPointListPage.getUpdatePeriods();
    }

    public UpdatePeriodType getUpdatePeriodType() {
        return editDataSourceWithPointListPage.getUpdatePeriodType();
    }
}
