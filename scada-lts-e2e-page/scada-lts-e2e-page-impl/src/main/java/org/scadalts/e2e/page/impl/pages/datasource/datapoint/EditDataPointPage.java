package org.scadalts.e2e.page.impl.pages.datasource.datapoint;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.*;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourcePage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;

import java.text.MessageFormat;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static javax.xml.bind.DatatypeConverter.parseBoolean;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAlertAfter;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAlertAfterClick;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhileNotVisible;

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

    public EditDataPointPage setDataPointName(DataPointIdentifier dataPointName) {
        delay();
        this.dataPointName.clear();
        this.dataPointName.setValue(dataPointName.getValue());
        return this;
    }

    public EditDataPointPage setDataPointXid(Xid dataPointXid) {
        delay();
        this.dataPointXid.clear();
        this.dataPointXid.setValue(dataPointXid.getValue());
        return this;
    }

    public EditDataPointPage setSettable(boolean settable) {
        return settable ? enableSettable() : this;
    }


    public EditDataPointPage enableSettable() {
        delay();
        settableCheckbox.click();
        return this;
    }

    public EditDataPointPage disableSettable() {
        delay();
        settableCheckbox.clear();
        return this;
    }

    public EditDataPointPage selectDataPointType(DataPointType dataPointType) {
        delay();
        acceptAlertAfter(dataTypes::selectOption, dataPointType.getName());
        return this;
    }

    public EditDataPointPage selectChangeType(ChangeType changeType) {
        delay();
        waitWhile(changeTypes, not(Condition.visible)).selectOption(changeType.getName());
        return this;
    }

    public EditDataPointPage setStartValue(DataPointCriteria criteria) {
        delay();
        String css = MessageFormat.format("td *[id=''{0}'']", DataPointChangeFieldType
                .getType(criteria, ChangeTypeField.START_VALUE).getId());
        waitWhile($(css), not(Condition.visible))
                .setValue(criteria.getStartValue());
        return this;
    }

    public EditDataPointPage saveDataPoint() {
        delay();
        acceptAlertAfterClick(saveDataPoint);
        return this;
    }

    public EditDataSourceWithPointListPage deleteDataPoint() {
        delay();
        deleteDataPoint.click();
        acceptAlertOnPage();
        return editDataSourceWithPointListPage;
    }

    public EditDataPointPage waitOnSettableCheckBox() {
        delay();
        waitWhileNotVisible(settableCheckbox);
        return this;
    }

    public String getDataPointName() {
        delay();
        return dataPointName.getValue();
    }

    public String getDataPointXid() {
        delay();
        return dataPointXid.getValue();
    }

    public boolean isSettable() {
        delay();
        return parseBoolean(settableCheckbox.getAttribute("selected"));
    }

    public DataPointType getDataTypes() {
        delay();
        return DataPointType.getType(dataTypes.getSelectedText());
    }

    public ChangeType getChangeTypes() {
        delay();
        return ChangeType.getType(changeTypes.getSelectedText());
    }

    public EditDataSourceWithPointListPage enableDataSource(boolean enable) {
        return editDataSourceWithPointListPage.enableDataSource(enable);
    }

    public EditDataSourceWithPointListPage enableAllDataPoint() {
        return editDataSourceWithPointListPage.enableAllDataPoint();
    }

    public EditDataPointPage addDataPoint() {
        return editDataSourceWithPointListPage.addDataPoint();
    }

    public EditDataPointPage openDataPointEditor(DataPointIdentifier dataPointIdentifier) {
        return editDataSourceWithPointListPage.openDataPointEditor(dataPointIdentifier);
    }

    public PropertiesDataPointPage openDataPointProperties(DataPointIdentifier dataPointIdentifier) {
        return editDataSourceWithPointListPage.openDataPointProperties(dataPointIdentifier);
    }

    public EditDataSourceWithPointListPage enableDataPoint(DataPointIdentifier dataPointIdentifier) {
        return editDataSourceWithPointListPage.enableDataPoint(dataPointIdentifier);
    }

    public EditDataSourceWithPointListPage disableDataPoint(DataPointIdentifier dataPointIdentifier) {
        return editDataSourceWithPointListPage.disableDataPoint(dataPointIdentifier);
    }

    public EditDataSourcePage setDataSourceName(DataSourceIdentifier dataSourceIdentifier) {
        return editDataSourceWithPointListPage.setDataSourceName(dataSourceIdentifier);
    }

    public EditDataSourcePage setDataSourceXid(Xid dataSourceXid) {
        return editDataSourceWithPointListPage.setDataSourceXid(dataSourceXid);
    }

    public EditDataSourcePage setUpdatePeriods(int updatePeriods) {
        return editDataSourceWithPointListPage.setUpdatePeriods(updatePeriods);
    }

    public EditDataSourcePage selectUpdatePeriodType(UpdatePeriodType updatePeriodType) {
        return editDataSourceWithPointListPage.selectUpdatePeriodType(updatePeriodType);
    }

    public String selectUpdatePeriodTypeValue(UpdatePeriodType updatePeriodType) {
        return editDataSourceWithPointListPage.selectUpdatePeriodTypeValue(updatePeriodType);
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
