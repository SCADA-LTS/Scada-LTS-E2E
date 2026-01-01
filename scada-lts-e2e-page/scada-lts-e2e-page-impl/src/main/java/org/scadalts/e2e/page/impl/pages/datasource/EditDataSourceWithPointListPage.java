package org.scadalts.e2e.page.impl.pages.datasource;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.core.utils.AlertUtil;
import org.scadalts.e2e.page.core.xpaths.XpathAttribute;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointPropertiesPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAfterClick;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.*;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

@Log4j2
public class EditDataSourceWithPointListPage extends PageObjectAbstract<EditDataSourceWithPointListPage> {

    @FindBy(id = "dsStatusImg")
    private SelenideElement dataSourceOnOff;

    @FindBy(id = "enableAllImg")
    private SelenideElement enableAllDataPoint;

    @FindBy(id = "editImg-1")
    private SelenideElement addDataPoint;

    @FindBy(css = "tbody #pointsList")
    private SelenideElement dataPointsTable;

    @FindBy(css = "tbody #pointListHeaders")
    private SelenideElement dataPointsTableHeader;

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
        super();
        editDataSourcePage = page(EditDataSourcePage.class);
    }

    public EditDataSourceWithPointListPage enable(boolean enable) {
        delay();
        waitWhile(dataSourceOnOff, not(Condition.visible));
        if(enable) {
            if($(SELECTOR_DISABLE_DATA_SOURCE_BY).is(Condition.visible)) {
                acceptAfterClick(dataSourceOnOff);
                waitWhile($(SELECTOR_ENABLE_DATA_SOURCE_BY), not(Condition.visible));
            }
        } else {
            if($(SELECTOR_ENABLE_DATA_SOURCE_BY).is(Condition.visible)) {
                AlertUtil.acceptAlert(dataSourceOnOff::clear);
                waitWhile($(SELECTOR_DISABLE_DATA_SOURCE_BY), not(Condition.visible));
            }
        }
        return this;
    }

    public EditDataSourceWithPointListPage setEnable(boolean enable) {
        return enable(enable);
    }

    public boolean isEnable() {
        delay();
        return $(SELECTOR_ENABLE_DATA_SOURCE_BY).is(Condition.visible);
    }

    public EditDataSourcePage setName(String name) {
        return editDataSourcePage.setName(new DataSourceIdentifier(name, DataSourceType.VIRTUAL_DATA_SOURCE));
    }

    public EditDataSourcePage setName(DataSourceIdentifier dataSourceIdentifier) {
        return editDataSourcePage.setName(dataSourceIdentifier);
    }

    public EditDataSourcePage setName(DataSourceCriteria criteria) {
        return setName(criteria.getIdentifier());
    }

    public EditDataSourcePage setXid(Xid dataSourceXid) {
        return editDataSourcePage.setXid(dataSourceXid);
    }

    public EditDataSourcePage setXid(String dataSourceXid) {
        return editDataSourcePage.setXid(new Xid(dataSourceXid));
    }

    public EditDataSourcePage setUpdatePeriods(int updatePeriods) {
        return editDataSourcePage.setUpdatePeriods(updatePeriods);
    }

    public EditDataSourcePage setUpdatePeriodType(UpdatePeriodType updatePeriodType) {
        return selectUpdatePeriodType(updatePeriodType);
    }

    public EditDataSourcePage setUpdatePeriodType(String updatePeriodType) {
        return selectUpdatePeriodType(UpdatePeriodType.getType(updatePeriodType));
    }

    public String getName() {
        return editDataSourcePage.getName();
    }

    public String getXid() {
        return editDataSourcePage.getXid();
    }

    public int getUpdatePeriods() {
        return editDataSourcePage.getUpdatePeriods();
    }

    public UpdatePeriodType getUpdatePeriodType() {
        return editDataSourcePage.getUpdatePeriodType();
    }


    public EditDataSourceWithPointListPage waitOnImgEnabledDataSource() {
        delay();
        waitWhile($(SELECTOR_ENABLE_DATA_SOURCE_BY), not(Condition.visible));
        return this;
    }

    public boolean isEnableDataPoint(DataPointIdentifier dataPointIdentifier) {
        return _findAction(dataPointIdentifier,SELECTOR_ACTION_ENABLE_DATA_POINT_BY).is(Condition.visible);
    }

    public EditDataSourceWithPointListPage enableAllDataPoint() {
        delay();
        waitWhile(enableAllDataPoint, not(Condition.visible));
        acceptAfterClick(enableAllDataPoint);
        waitWhile($(SELECTOR_ACTION_ENABLE_DATA_POINT_BY), not(Condition.visible));
        return this;
    }

    @Override
    public EditDataSourceWithPointListPage getPage() {
        return this;
    }

    public EditDataPointPage addDataPoint() {
        delay();
        acceptAfterClick(addDataPoint);
        return page(EditDataPointPage.class);
    }

    public EditDataPointPage openDataPointEditor(DataPointIdentifier dataPointIdentifier) {
        acceptAfterClick(_findAction(dataPointIdentifier, SELECTOR_ACTION_EDIT_DATA_POINT_BY));
        printCurrentUrl();
        return page(EditDataPointPage.class);
    }

    public DataPointPropertiesPage openDataPointProperties(DataPointIdentifier dataPointIdentifier) {
        _findAction(dataPointIdentifier, SELECTOR_ACTION_PROPERTIES_DATA_POINT_BY).click();
        printCurrentUrl();
        return page(new DataPointPropertiesPage(this));
    }

    public EditDataSourceWithPointListPage enableDataPoint(DataPointIdentifier dataPointIdentifier) {
        waitWhile(_findAction(dataPointIdentifier, SELECTOR_ACTION_DISABLE_DATA_POINT_BY), not(Condition.visible)).click();
        waitWhile(_findAction(dataPointIdentifier, SELECTOR_ACTION_ENABLE_DATA_POINT_BY), not(Condition.visible));
        return this;
    }

    public EditDataSourceWithPointListPage disableDataPoint(DataPointIdentifier dataPointIdentifier) {
        acceptAfterClick(waitWhile(_findAction(dataPointIdentifier, SELECTOR_ACTION_ENABLE_DATA_POINT_BY), not(Condition.visible)));
        waitWhile(_findAction(dataPointIdentifier, SELECTOR_ACTION_DISABLE_DATA_POINT_BY), not(Condition.visible));
        return this;
    }

    public EditDataPointPage openDataPointEditor(DataPointCriteria criteria) {
        return openDataPointEditor(criteria.getIdentifier());
    }

    public DataPointPropertiesPage openDataPointProperties(DataPointCriteria criteria) {
        return openDataPointProperties(criteria.getIdentifier());
    }

    public EditDataSourceWithPointListPage enableDataPoint(DataPointCriteria criteria) {
       return enableDataPoint(criteria.getIdentifier());
    }

    public EditDataSourceWithPointListPage disableDataPoint(DataPointCriteria criteria) {
        return disableDataPoint(criteria.getIdentifier());
    }


    public EditDataSourcePage selectUpdatePeriodType(UpdatePeriodType updatePeriodType) {
        return editDataSourcePage.selectUpdatePeriodType(updatePeriodType);
    }

    public String selectUpdatePeriodTypeValue(UpdatePeriodType updatePeriodType) {
        return editDataSourcePage.selectUpdatePeriodTypeValue(updatePeriodType);
    }

    public EditDataSourceWithPointListPage save() {
        return editDataSourcePage.save();
    }


    public EditDataSourceWithPointListPage waitOnPageWhileVisibleDataPoint(DataPointIdentifier identifier) {
        waitWhile(_findObject(identifier), Condition.visible);
        return this;
    }

    @Deprecated
    public int getDataPointDatabaseId(DataPointIdentifier identifier) {
        SelenideElement selenideElement = waitWhile($(_findAction(identifier, SELECTOR_ACTION_PROPERTIES_DATA_POINT_BY)), not(Condition.visible));
        String onclick = selenideElement.getAttribute("onclick");
        return convertToInt(onclick);
    }

    public EditDataSourceWithPointListPage waitOnPointsTable() {
        waitWhile(dataPointsTable, not(Condition.visible));
        return this;
    }

    @Override
    public boolean containsObject(IdentifierObject identifier) {
        waitWhile(dataPointsTable, not(Condition.exist), true);
        waitWhile(dataPointsTableHeader, not(Condition.visible), true);
        String text = dataPointsTable.innerText();
        if(text.isEmpty()) {
            return false;
        }
        NodeCriteria bCriteria = NodeCriteria.exactly(Tag.b(), XpathAttribute.text(identifier.getValue()));
        SelenideElement bElement = findObject(bCriteria, dataPointsTable);
        return bElement.is(Condition.visible);
    }

    private SelenideElement _findAction(DataPointIdentifier identifier, By selectAction) {
        delay();
        return findAction(identifier.getNodeCriteria(), selectAction, dataPointsTable);
    }

    private SelenideElement _findObject(DataPointIdentifier identifier) {
        delay();
        return findObject(identifier.getNodeCriteria(), dataPointsTable);
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

    @Override
    public EditDataSourceWithPointListPage waitForCompleteLoad() {
        waitWhile(dataPointsTable, not(Condition.visible), true);
        return super.waitForCompleteLoad();
    }
}
