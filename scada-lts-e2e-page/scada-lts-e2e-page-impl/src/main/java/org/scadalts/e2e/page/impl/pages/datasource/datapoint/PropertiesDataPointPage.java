package org.scadalts.e2e.page.impl.pages.datasource.datapoint;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventDetectorIdentifier;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static java.text.MessageFormat.format;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhileNotVisible;

@Log4j2
public class PropertiesDataPointPage extends PageObjectAbstract<PropertiesDataPointPage> {

    @FindBy(id = "eventDetectorSelect")
    private SelenideElement eventDetectorSelect;

    @FindBy(css = "a[href*='data_source_edit.shtm?dsid']")
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

    public PropertiesDataPointPage(EditDataSourceWithPointListPage editDataSourceWithPointListPage) {
        super(TITLE);
        this.editDataSourceWithPointListPage = editDataSourceWithPointListPage;
    }

    public PropertiesDataPointPage selectEventDetectorType(EventDetectorType eventDetectorType) {
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
        String css = format(INPUT_ALIAS, detectorPosition);
        $(By.cssSelector(css)).setValue(eventDetectorName.getValue());
        return this;
    }

    public PropertiesDataPointPage setXid(Xid eventDetectorXid, int detectorPosition) {
        String css = format(INPUT_XID, detectorPosition);
        $(By.cssSelector(css)).setValue(eventDetectorXid.getValue());
        return this;
    }

    public PropertiesDataPointPage selectAlarmLevel(AlarmLevel alarmLevel, int detectorPosition) {
        String css = format(SELECT_ALARM_LIST, detectorPosition);
        $(By.cssSelector(css)).selectOption(alarmLevel.getName());
        return this;
    }

    public AlarmLevel getAlarmLevel() {
        String value = $(By.cssSelector(GET_FIRST_SELECT_ALARM_LIST)).getText();
        return AlarmLevel.getType(value);
    }

    public Xid getXid() {
        String value = $(By.cssSelector(GET_FIRST_INPUT_XID)).getValue();
        return new Xid(value);
    }

    public EventDetectorIdentifier getAlias() {
        String value = $(By.cssSelector(GET_FIRST_INPUT_ALIAS)).getValue();
        return new EventDetectorIdentifier(value);
    }

    public PropertiesDataPointPage saveDataPoint() {
        saveDataPoint.click();
        return this;
    }

    public EditDataSourceWithPointListPage editDataSource() {
        editDataSource.click();
        return editDataSourceWithPointListPage;
    }

    public PropertiesDataPointPage addEventDetector() {
        addEventDetector.click();
        return this;
    }

    public PropertiesDataPointPage deleteEventDetector(EventDetectorCriteria criteria) {
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
    public boolean containsObject(CriteriaObject criteria) {
        ElementsCollection elements = waitWhile(eventDetectorTable, not(Condition.visible)).$$(By.tagName("input"));
        for (SelenideElement element: elements) {
            String value = element.getValue();
            if(value != null && value.equals(criteria.getIdentifier().getValue()))
                return true;
        }
        return false;
    }

    public PropertiesDataPointPage waitOnEventDetectorTable() {
        waitWhileNotVisible(eventDetectorTable);
        return this;
    }

    public String getEventDetectorTableHtml() {
        return waitWhileNotVisible(eventDetectorTable).innerHtml();
    }

    @Override
    public PropertiesDataPointPage getPage() {
        return this;
    }
}
