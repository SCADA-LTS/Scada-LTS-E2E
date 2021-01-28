package org.scadalts.e2e.page.impl.pages.datasource;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointPropertiesPage;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAlertAfter;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAlertAfterClick;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findAction;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;


public class EditDataSourceWithPointListPage extends PageObjectAbstract<EditDataSourceWithPointListPage> {

    @FindBy(id = "dsStatusImg")
    private SelenideElement dataSourceOnOff;

    @FindBy(id = "enableAllImg")
    private SelenideElement enableAllDataPoint;

    @FindBy(id = "editImg-1")
    private SelenideElement addDataPoint;

    @FindBy(css = "tbody #pointsList")
    private SelenideElement dataPointsTable;

    @FindBy(id = "dataSourceProperties")
    private SelenideElement dataSourceProperties;

    private EditDataSourcePage editDataSourcePage;

    private static final By SELECTOR_ACTION_EDIT_DATA_POINT_BY = By.cssSelector("img[onclick*='editPoint(']");
    private static final By SELECTOR_ACTION_PROPERTIES_DATA_POINT_BY = By.cssSelector("img[onclick*='data_point_edit.shtm?dpid=']");
    private static final By SELECTOR_ACTION_ENABLE_DATA_POINT_BY = By.cssSelector("img[src='images/brick_go.png']");
    private static final By SELECTOR_ACTION_DISABLE_DATA_POINT_BY = By.cssSelector("img[src='images/brick_stop.png']");
    private static final By SELECTOR_DISABLE_DATA_SOURCE_BY = By.cssSelector("img[src='images/database_stop.png']");
    private static final By SELECTOR_ENABLE_DATA_SOURCE_BY = By.cssSelector("img[src='images/database_go.png']");


    public static final String TITLE = "Points";

    public EditDataSourceWithPointListPage() {
        super(TITLE);
        editDataSourcePage = page(EditDataSourcePage.class);
    }

    public EditDataSourceWithPointListPage enableDataSource(boolean enable) {
        delay();
        if(enable) {
            if($(SELECTOR_DISABLE_DATA_SOURCE_BY).is(Condition.visible)) {
                acceptAlertAfterClick(dataSourceOnOff);
                waitWhile($(SELECTOR_ENABLE_DATA_SOURCE_BY), not(Condition.visible));
            }
        } else {
            if($(SELECTOR_ENABLE_DATA_SOURCE_BY).is(Condition.visible)) {
                acceptAlertAfter(dataSourceOnOff::clear);
                waitWhile($(SELECTOR_DISABLE_DATA_SOURCE_BY), not(Condition.visible));
            }
        }
        return this;
    }

    public EditDataSourceWithPointListPage waitOnImgEabledDataSource() {
        delay();
        waitWhile($(SELECTOR_ENABLE_DATA_SOURCE_BY), not(Condition.visible));
        return this;
    }

    public boolean isEnableDataSource() {
        delay();
        return $(SELECTOR_ENABLE_DATA_SOURCE_BY).is(Condition.visible);
    }

    public boolean isEnableDataPoint(DataPointIdentifier dataPointIdentifier) {
        return _findAction(dataPointIdentifier,SELECTOR_ACTION_ENABLE_DATA_POINT_BY).is(Condition.visible);
    }

    public EditDataSourceWithPointListPage enableAllDataPoint() {
        delay();
        acceptAlertAfterClick(enableAllDataPoint);
        waitWhile($(SELECTOR_ACTION_ENABLE_DATA_POINT_BY), not(Condition.visible));
        return this;
    }

    @Override
    public EditDataSourceWithPointListPage getPage() {
        return this;
    }

    public EditDataPointPage addDataPoint() {
        delay();
        acceptAlertAfterClick(addDataPoint);
        return page(EditDataPointPage.class);
    }

    public EditDataPointPage openDataPointEditor(DataPointIdentifier dataPointIdentifier) {
        acceptAlertAfterClick(_findAction(dataPointIdentifier, SELECTOR_ACTION_EDIT_DATA_POINT_BY));
        return page(EditDataPointPage.class);
    }

    public DataPointPropertiesPage openDataPointProperties(DataPointIdentifier dataPointIdentifier) {
        _findAction(dataPointIdentifier, SELECTOR_ACTION_PROPERTIES_DATA_POINT_BY).click();
        return page(new DataPointPropertiesPage(this));
    }

    public EditDataSourceWithPointListPage enableDataPoint(DataPointIdentifier dataPointIdentifier) {
        _findAction(dataPointIdentifier, SELECTOR_ACTION_DISABLE_DATA_POINT_BY).click();
        waitWhile(_findAction(dataPointIdentifier, SELECTOR_ACTION_ENABLE_DATA_POINT_BY), not(Condition.visible));
        return this;
    }

    public EditDataSourceWithPointListPage disableDataPoint(DataPointIdentifier dataPointIdentifier) {
        acceptAlertAfterClick(_findAction(dataPointIdentifier, SELECTOR_ACTION_ENABLE_DATA_POINT_BY));
        waitWhile($(_findAction(dataPointIdentifier, SELECTOR_ACTION_DISABLE_DATA_POINT_BY)), not(Condition.visible));
        return this;
    }

    public EditDataSourcePage setDataSourceName(DataSourceIdentifier dataSourceIdentifier) {
        return editDataSourcePage.setDataSourceName(dataSourceIdentifier);
    }

    public EditDataSourcePage setDataSourceXid(Xid dataSourceXid) {
        return editDataSourcePage.setDataSourceXid(dataSourceXid);
    }

    public EditDataSourcePage setUpdatePeriods(int updatePeriods) {
        return editDataSourcePage.setUpdatePeriods(updatePeriods);
    }

    public EditDataSourcePage selectUpdatePeriodType(UpdatePeriodType updatePeriodType) {
        return editDataSourcePage.selectUpdatePeriodType(updatePeriodType);
    }

    public String selectUpdatePeriodTypeValue(UpdatePeriodType updatePeriodType) {
        return editDataSourcePage.selectUpdatePeriodTypeValue(updatePeriodType);
    }

    public EditDataSourceWithPointListPage saveDataSource() {
        return editDataSourcePage.saveDataSource();
    }

    public String getDataSourceName() {
        return editDataSourcePage.getDataSourceName();
    }

    public String getDataSourceXid() {
        return editDataSourcePage.getDataSourceXid();
    }

    public int getUpdatePeriods() {
        return editDataSourcePage.getUpdatePeriods();
    }

    public UpdatePeriodType getUpdatePeriodType() {
        return editDataSourcePage.getUpdatePeriodType();
    }

    public EditDataSourceWithPointListPage waitOnPageWhileVisibleObject(DataPointIdentifier identifier) {
        waitWhile(_findObject(identifier), Condition.visible);
        return this;
    }

    private SelenideElement _findAction(DataPointIdentifier identifier, By selectAction) {
        delay();
        return findAction(identifier.getNodeCriteria(), selectAction, dataPointsTable);
    }

    private SelenideElement _findObject(DataPointIdentifier identifier) {
        delay();
        return findObject(identifier.getNodeCriteria(), dataPointsTable);
    }

    @Deprecated
    public int getDataPointDatabaseId(DataPointIdentifier identifier) {
        SelenideElement selenideElement = waitWhile($(_findAction(identifier, SELECTOR_ACTION_PROPERTIES_DATA_POINT_BY)), not(Condition.visible));
        String onclick = selenideElement.getAttribute("onclick");
        return convertToInt(onclick);
    }

    private int convertToInt(String onclick) {
        if(onclick != null) {
            String[] split = onclick.split("dpid=");
            if (split.length == 2) {
                String onlyNumber = split[1].replaceAll("[^0-9]", "");
                if(!onlyNumber.isEmpty())
                    return Integer.parseInt(onlyNumber);
            }
        }
        return -1;
    }
}
