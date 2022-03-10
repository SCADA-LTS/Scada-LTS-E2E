package org.scadalts.e2e.page.impl.pages.datasource.datapoint;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.core.utils.AlertUtil;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.dicts.ChangeType;
import org.scadalts.e2e.page.impl.dicts.ChangeTypeField;
import org.scadalts.e2e.page.impl.dicts.DataPointChangeFieldType;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;

import java.text.MessageFormat;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static javax.xml.bind.DatatypeConverter.parseBoolean;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAfterClick;
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

    @FindBy(id = "pointDetails")
    private SelenideElement pointDetails;

    private EditDataSourceWithPointListPage editDataSourceWithPointListPage;

    public static final String TITLE = "Point details";

    public EditDataPointPage() {
        super();
        editDataSourceWithPointListPage = page(EditDataSourceWithPointListPage.class);
    }

    @Override
    public EditDataPointPage getPage() {
        return this;
    }

    public EditDataPointPage setName(DataPointIdentifier identifier) {
        return setName(identifier.getValue());
    }

    public EditDataPointPage setName(String dataPointName) {
        delay();
        this.dataPointName.clear();
        this.dataPointName.setValue(dataPointName);
        return this;
    }

    public EditDataPointPage setXid(Xid dataPointXid) {
        return setXid(dataPointXid.getValue());
    }

    public EditDataPointPage setXid(String dataPointXid) {
        delay();
        this.dataPointXid.clear();
        this.dataPointXid.setValue(dataPointXid);
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

    public EditDataPointPage setDataPointType(String dataPointType) {
        delay();
        AlertUtil.acceptAlert(dataTypes::selectOption, dataPointType);
        return this;
    }

    public EditDataPointPage setDataPointType(DataPointType dataPointType) {
        return setDataPointType(dataPointType.getName());
    }

    public EditDataPointPage setChangeType(String changeType) {
        delay();
        waitWhile(changeTypes, not(Condition.visible)).selectOption(changeType);
        return this;
    }

    public EditDataPointPage setChangeType(ChangeType changeType) {
        return setChangeType(changeType.getName());
    }

    public EditDataPointPage setStartValue(DataPointCriteria criteria) {
        delay();
        String css = MessageFormat.format("td *[id=''{0}'']", DataPointChangeFieldType
                .getType(criteria, ChangeTypeField.START_VALUE).getId());
        waitWhile($(css), not(Condition.visible))
                .setValue(criteria.getStartValue());
        return this;
    }

    public String getMsgStartValue(DataPointCriteria criteria) {
        delay();
        String css = MessageFormat.format("td *[id=''{0}'']", DataPointChangeFieldType
                .getType(criteria, ChangeTypeField.START_VALUE).getId());
        waitWhile($(css), not(Condition.visible))
                .setValue(criteria.getStartValue());
        return "";
    }

    public EditDataPointPage save() {
        delay();
        acceptAfterClick(saveDataPoint);
        /*String msg = waitWhile($(By.id("pointDetails")).$(By.id("pointMessage")), not(Condition.visible)).getText();
        if(!msg.contains("saved")) {
            throw new E2eTestException("Save is failed");
        }*/
        return this;
    }

    public EditDataSourceWithPointListPage deleteDataPoint() {
        delay();
        deleteDataPoint.click();
        //acceptAlertOnPage();
        return editDataSourceWithPointListPage;
    }

    public EditDataPointPage waitOnSettableCheckBox() {
        delay();
        waitWhileNotVisible(settableCheckbox);
        return this;
    }

    public String getName() {
        delay();
        return dataPointName.getValue();
    }

    public String getXid() {
        delay();
        return dataPointXid.getValue();
    }

    public boolean isSettable() {
        delay();
        return parseBoolean(settableCheckbox.getAttribute("selected"));
    }

    public DataPointType getDataType() {
        delay();
        return DataPointType.getType(dataTypes.getSelectedText());
    }

    public ChangeType getChangeTypes() {
        delay();
        return ChangeType.getType(changeTypes.getSelectedText());
    }

    public EditDataPointPage openDataPointEditor(DataPointIdentifier dataPointIdentifier) {
        return editDataSourceWithPointListPage.openDataPointEditor(dataPointIdentifier);
    }

    public DataPointPropertiesPage openDataPointProperties(DataPointIdentifier dataPointIdentifier) {
        return editDataSourceWithPointListPage.openDataPointProperties(dataPointIdentifier);
    }

    public EditDataSourceWithPointListPage enableDataPoint(DataPointIdentifier dataPointIdentifier) {
        return editDataSourceWithPointListPage.enableDataPoint(dataPointIdentifier);
    }

    public EditDataSourceWithPointListPage disableDataPoint(DataPointIdentifier dataPointIdentifier) {
        return editDataSourceWithPointListPage.disableDataPoint(dataPointIdentifier);
    }

    public EditDataPointPage openDataPointEditor(DataPointCriteria criteria) {
        return editDataSourceWithPointListPage.openDataPointEditor(criteria.getIdentifier());
    }

    public DataPointPropertiesPage openDataPointProperties(DataPointCriteria criteria) {
        return editDataSourceWithPointListPage.openDataPointProperties(criteria.getIdentifier());
    }

    public EditDataSourceWithPointListPage enableDataPoint(DataPointCriteria criteria) {
        return editDataSourceWithPointListPage.enableDataPoint(criteria.getIdentifier());
    }

    public EditDataSourceWithPointListPage disableDataPoint(DataPointCriteria criteria) {
        return editDataSourceWithPointListPage.disableDataPoint(criteria.getIdentifier());
    }
}
