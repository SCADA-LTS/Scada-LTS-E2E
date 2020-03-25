package org.scadalts.e2e.page.impl.pages.datasource.datapoint;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventDetectorIdentifier;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.or;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.text.MessageFormat.format;
import static org.scadalts.e2e.page.core.criterias.Tag.*;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAlertAfterClick;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhileNotVisible;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.onclick;

@Log4j2
public class PropertiesDataPointPage extends PageObjectAbstract<PropertiesDataPointPage> {

    @FindBy(id = "eventDetectorSelect")
    private SelenideElement eventDetectorSelect;

    @FindBy(css = "a[href*='data_source_edit.shtm']")
    private SelenideElement editDataSource;

    @FindBy(css = "input[onclick*=\"doSave('save')\"]")
    private SelenideElement saveDataPoint;

    @FindBy(css = "img[onclick*='addEventDetector']")
    private SelenideElement addEventDetector;

    @FindBy(id = "eventDetectorTable")
    private SelenideElement eventDetectorTable;

    private final EditDataSourceWithPointListPage editDataSourceWithPointListPage;

    public final static String TITLE = "";

    private final static String SELECT_ALARM_LIST = "select[id*='''-'{0}'AlarmLevel''']";
    private final static String INPUT_ALIAS = "input[id*='''-'{0}'Alias''']";
    private final static String INPUT_XID = "input[id*='''-'{0}'Xid''']";

    private final static String GET_FIRST_SELECT_ALARM_LIST = "select[id*='AlarmLevel']";
    private final static String GET_FIRST_INPUT_ALIAS = "input[id*='Alias']";
    private final static String GET_FIRST_INPUT_XID = "input[id*='Xid']";
    private final static String GET_EVENT_DETECTOR_DATA = "tbody[id*='eventDetector']";

    public PropertiesDataPointPage(EditDataSourceWithPointListPage editDataSourceWithPointListPage) {
        super(TITLE);
        this.editDataSourceWithPointListPage = editDataSourceWithPointListPage;
    }

    public PropertiesDataPointPage selectEventDetectorType(EventDetectorType eventDetectorType) {
        delay();
        eventDetectorSelect.selectOption(eventDetectorType.getName());
        return this;
    }

    public PropertiesDataPointPage selectAlarmLevel(AlarmLevel alarmLevel) {
        return selectAlarmLevel(alarmLevel, 1);
    }

    public PropertiesDataPointPage setAlias(EventDetectorIdentifier eventDetectorName) {
        return setAlias(eventDetectorName, 1);
    }

    public PropertiesDataPointPage setXid(Xid eventDetectorXid) {
        return setXid(eventDetectorXid, 1);
    }

    public PropertiesDataPointPage setAlias(EventDetectorIdentifier eventDetectorName, int detectorPosition) {
        delay();
        String css = format(INPUT_ALIAS, detectorPosition);
        $(By.cssSelector(css)).setValue(eventDetectorName.getValue());
        return this;
    }

    public PropertiesDataPointPage setXid(Xid eventDetectorXid, int detectorPosition) {
        delay();
        String css = format(INPUT_XID, detectorPosition);
        $(By.cssSelector(css)).setValue(eventDetectorXid.getValue());
        return this;
    }

    public PropertiesDataPointPage selectAlarmLevel(AlarmLevel alarmLevel, int detectorPosition) {
        delay();
        String css = format(SELECT_ALARM_LIST, detectorPosition);
        $(By.cssSelector(css)).selectOption(alarmLevel.getName());
        return this;
    }

    public AlarmLevel getAlarmLevelFirst() {
        return getAlarmLevel(0);
    }

    public Xid getXidFirst() {
        return getXid(0);
    }

    public EventDetectorIdentifier getAliasFirst() {
        return getAlias(0);
    }


    public AlarmLevel getAlarmLevel(int detectorPosition) {
        delay();
        waitOnEventDetectorTable();
        SelenideElement eventDetectorData = _getEventDetectorData(detectorPosition);
        String value = waitWhile(eventDetectorData.$(By.cssSelector(GET_FIRST_SELECT_ALARM_LIST)), not(Condition.visible)).getSelectedText();
        return AlarmLevel.getType(value);
    }

    public Xid getXid(int detectorPosition) {
        delay();
        waitOnEventDetectorTable();
        SelenideElement eventDetectorData = _getEventDetectorData(detectorPosition);
        String value = waitWhile(eventDetectorData.$(By.cssSelector(GET_FIRST_INPUT_XID)), not(Condition.visible)).getValue();
        return new Xid(value);
    }

    public EventDetectorIdentifier getAlias(int detectorPosition) {
        String value = getAliasValue(detectorPosition);
        EventDetectorType type = getEventDetectorType(detectorPosition);
        return new EventDetectorIdentifier(value, type);
    }

    public String getAliasValue(int detectorPosition) {
        delay();
        waitOnEventDetectorTable();
        SelenideElement eventDetectorData = _getEventDetectorData(detectorPosition);
        return waitWhile(eventDetectorData.$(By.cssSelector(GET_FIRST_INPUT_ALIAS)), not(Condition.visible)).getValue();
    }

    public EventDetectorType getEventDetectorTypeFirst() {
        return getEventDetectorType(0);
    }

    public EventDetectorType getEventDetectorType(int detectorPosition) {
        delay();
        waitOnEventDetectorTable();
        SelenideElement eventDetectorData = _getEventDetectorData(detectorPosition);
        NodeCriteria trWithTdTypeCriteria = NodeCriteria.withNode(tr(), img(), onclick("deleteDetector"));

        SelenideElement trWithTdType = findObject(trWithTdTypeCriteria, eventDetectorData);
        NodeCriteria tdWithTypeCriteria = NodeCriteria.every(2, 0, td());

        SelenideElement tdWithType = findObject(tdWithTypeCriteria, trWithTdType);
        return EventDetectorType.getTypeContains(tdWithType.getText());
    }

    public PropertiesDataPointPage saveDataPoint() {
        delay();
        waitWhile(saveDataPoint, not(Condition.visible)).click();
        waitWhile($(By.cssSelector(".content > table tbody tr td > table .formError")), or(" empty or not visible", Condition.empty, not(Condition.visible)));
        waitOnPage(3000);
        return this;
    }

    public EditDataSourceWithPointListPage editDataSource() {
        delay();
        acceptAlertAfterClick(editDataSource);
        return editDataSourceWithPointListPage;
    }

    public PropertiesDataPointPage addEventDetector() {
        delay();
        addEventDetector.click();
        return this;
    }

    public PropertiesDataPointPage deleteEventDetector(EventDetectorCriteria criteria) {
        delay();
        ElementsCollection elements = waitWhile(eventDetectorTable, not(Condition.visible)).$$(By.tagName("tbody"));
        for (SelenideElement element: elements) {
            ElementsCollection inputs = element.$$(By.tagName("input"));
            for (SelenideElement input: inputs) {
                String value = input.getValue();
                if(value != null && value.equals(criteria.getIdentifier().getValue())) {
                    element.$(By.cssSelector("img[onclick*='deleteDetector']")).click();
                }
            }
        }
        return this;
    }

    @Override
    public boolean containsObject(IdentifierObject identifier) {
        delay();
        ElementsCollection elements = waitWhile(eventDetectorTable, not(Condition.visible)).$$(By.tagName("input"));
        logger.info("number detectors: {}", elements.size()/2);
        for (SelenideElement element: elements) {
            logger.debug("element: {}", element);
            String value = element.getValue();
            if(value != null && value.equals(identifier.getValue()))
                return true;
        }
        return false;
    }

    public PropertiesDataPointPage waitOnPageWhileNotVisible(EventDetectorIdentifier eventDetectorIdentifier) {
        waitWhile(a -> !this.containsObject(a), eventDetectorIdentifier);
        return this;
    }

    public PropertiesDataPointPage waitOnEventDetectorTable() {
        delay();
        waitWhileNotVisible(eventDetectorTable);
        return this;
    }

    public String getEventDetectorTableHtml() {
        delay();
        return waitWhileNotVisible(eventDetectorTable).innerHtml();
    }

    @Override
    public PropertiesDataPointPage getPage() {
        return this;
    }


    private SelenideElement _getEventDetectorData(int position) {
        ElementsCollection collectionElement = $$(By.cssSelector(GET_EVENT_DETECTOR_DATA));
        return collectionElement.get(position);
    }
}
