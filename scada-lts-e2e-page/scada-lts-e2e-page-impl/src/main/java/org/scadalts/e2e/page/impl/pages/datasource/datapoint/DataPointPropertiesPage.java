package org.scadalts.e2e.page.impl.pages.datasource.datapoint;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.javascripts.DojoScripts;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventDetectorIdentifier;
import org.scadalts.e2e.page.impl.dicts.*;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;

import java.math.BigDecimal;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.or;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.text.MessageFormat.format;
import static org.scadalts.e2e.page.core.criterias.Tag.*;
import static org.scadalts.e2e.page.core.javascripts.JQueryScripts.attrSelected;
import static org.scadalts.e2e.page.core.javascripts.JQueryScripts.val;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAfterClick;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhileNotVisible;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.onclick;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.selected;
import static org.scadalts.e2e.page.impl.pages.datasource.datapoint.JavaBeanUtil.*;

@Log4j2
public class DataPointPropertiesPage extends PageObjectAbstract<DataPointPropertiesPage> {

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

    @FindBy(id = "loggingType")
    private SelenideElement loggingTypeSelect;

    @FindBy(id = "purgeType")
    private SelenideElement purgeTypeSelect;

    @FindBy(id = "purgePeriod")
    private SelenideElement purgePeriodInput;

    @FindBy(id = "defaultCacheSize")
    private SelenideElement defaultCacheSizeInput;

    @FindBy(id = "clearCacheBtn")
    private SelenideElement clearCacheButton;

    @FindBy(css = "input[name='name']")
    private SelenideElement dataPointNameInput;

    @FindBy(css = "select[name='engineeringUnits']")
    private SelenideElement engineeringUnitsSelect;

    @FindBy(css = "div[class='content']")
    private SelenideElement contentPage;

    @FindBy(id= "textRendererSelect")
    private SelenideElement textRendererSelect;

    @FindBy(id= "chartRendererSelect")
    private SelenideElement chartRendererSelect;

    private final EditDataSourceWithPointListPage editDataSourceWithPointListPage;

    public final static String TITLE = "";

    private final static String SELECT_ALARM_LIST = "select[id*='''-'{0}'AlarmLevel''']";
    private final static String INPUT_ALIAS = "input[id*='''-'{0}'Alias''']";
    private final static String INPUT_XID = "input[id*='''-'{0}'Xid''']";

    private final static String GET_FIRST_SELECT_ALARM_LIST = "select[id*='AlarmLevel']";
    private final static String GET_FIRST_INPUT_ALIAS = "input[id*='Alias']";
    private final static String GET_FIRST_INPUT_XID = "input[id*='Xid']";
    private final static String GET_EVENT_DETECTOR_DATA = "tbody[id*='eventDetector']";

    private static final By INTERVAL_LOGGING_PERIOD_VALUE_INPUT_BY = By.cssSelector("input[name*='intervalLoggingPeriod']");
    private static final By INTERVAL_LOGGING_PERIOD_TYPE_SELECT_BY = By.cssSelector("select[name*='intervalLoggingPeriodType']");
    private static final By INTERVAL_LOGGING_TYPE_SELECT_BY = By.id("intervalLoggingType");
    private static final By TOLERANCE_INPUT_BY = By.id("tolerance");
    private static final By DISCARD_EXTREME_VALUES_CHECKBOX_BY = By.id("discardExtremeValues");
    private static final By DISCARD_LOW_LIMIT_INPUT_BY = By.id("discardLowLimit");
    private static final By DISCARD_HIGH_LIMIT_INPUT_BY = By.id("discardHighLimit");
    private static final String TEXT_RENDERER_SUFFIX_INPUT = "input[id*='Suffix']";
    private static final String TEXT_RENDERER_FORMAT_INPUT = "input[id*='Format']";

    private static final By TEXT_RENDERER_FROM_INPUT_BY = By.cssSelector("input[id='textRendererRangeFrom']");
    private static final By TEXT_RENDERER_TO_INPUT_BY = By.cssSelector("input[id='textRendererRangeTo']");
    private static final String TEXT_RENDERER_TEXT_INPUT = "input[id*='Text']";
    private static final By ADD_RANGE_IMG_BY = By.cssSelector("img[onclick*='addRangeValue']");
    private static final By TIME_CONVERSION_EXPONENT_INPUT_BY = By.cssSelector("input[id*='ConversionExponent']");

    private static final String CHART_RENDERER_PERIOD_INPUT = "input[id*='NumberOfPeriods']";
    private static final String CHART_RENDERER_PERIOD_TYPE_SELECT = "select[id*='TimePeriod']";
    private static final String CHART_RENDERER_INCLUDE_SUM_CHECKBOX = "input[id='chartRendererStatsIncludeSum']";
    private static final String CLICKED_COLOR_TD = "td[style*=''{0}'']";

    private static final By CHART_RENDERER_LIMIT_INPUT_BY = By.cssSelector("input[id='chartRendererTableLimit']");


    public DataPointPropertiesPage(EditDataSourceWithPointListPage editDataSourceWithPointListPage) {
        super();
        this.editDataSourceWithPointListPage = editDataSourceWithPointListPage;
    }

    public DataPointPropertiesPage selectEventDetectorType(EventDetectorType eventDetectorType) {
        delay();
        eventDetectorSelect.selectOption(eventDetectorType.getName());
        return this;
    }

    public DataPointPropertiesPage selectEventDetectorAlarmLevel(AlarmLevel alarmLevel) {
        return selectEventDetectorAlarmLevel(alarmLevel, 1);
    }

    public DataPointPropertiesPage setEventDetectorAlias(EventDetectorIdentifier eventDetectorName) {
        return setEventDetectorAlias(eventDetectorName, 1);
    }

    public DataPointPropertiesPage setEventDetectorXid(Xid eventDetectorXid) {
        return setEventDetectorXid(eventDetectorXid, 1);
    }

    public DataPointPropertiesPage setEventDetectorAlias(EventDetectorIdentifier eventDetectorName, int detectorPosition) {
        delay();
        String css = format(INPUT_ALIAS, detectorPosition);
        $(By.cssSelector(css)).setValue(eventDetectorName.getValue());
        return this;
    }

    public DataPointPropertiesPage setEventDetectorXid(Xid eventDetectorXid, int detectorPosition) {
        delay();
        String css = format(INPUT_XID, detectorPosition);
        $(By.cssSelector(css)).setValue(eventDetectorXid.getValue());
        return this;
    }

    public DataPointPropertiesPage selectEventDetectorAlarmLevel(AlarmLevel alarmLevel, int detectorPosition) {
        delay();
        String css = format(SELECT_ALARM_LIST, detectorPosition);
        $(By.cssSelector(css)).selectOption(alarmLevel.getName());
        return this;
    }

    public AlarmLevel getEventDetectorAlarmLevelFirst() {
        return getEventDetectorAlarmLevel(0);
    }

    public Xid getEventDetectorXidFirst() {
        return getEventDetectorXid(0);
    }

    public EventDetectorIdentifier getEventDetectorAliasFirst() {
        return getEventDetectorAlias(0);
    }


    public AlarmLevel getEventDetectorAlarmLevel(int detectorPosition) {
        delay();
        waitOnEventDetectorTable();
        SelenideElement eventDetectorData = _getEventDetectorData(detectorPosition);
        String value = waitWhile(eventDetectorData.$(By.cssSelector(GET_FIRST_SELECT_ALARM_LIST)), not(Condition.visible)).getSelectedText();
        return AlarmLevel.getTypeByName(value);
    }

    public Xid getEventDetectorXid(int detectorPosition) {
        delay();
        waitOnEventDetectorTable();
        SelenideElement eventDetectorData = _getEventDetectorData(detectorPosition);
        String value = waitWhile(eventDetectorData.$(By.cssSelector(GET_FIRST_INPUT_XID)), not(Condition.visible)).getValue();
        return new Xid(value);
    }

    public EventDetectorIdentifier getEventDetectorAlias(int detectorPosition) {
        String value = getEventDetectorAliasValue(detectorPosition);
        EventDetectorType type = getEventDetectorType(detectorPosition);
        return new EventDetectorIdentifier(value, type);
    }

    public String getEventDetectorAliasValue(int detectorPosition) {
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

    public DataPointPropertiesPage saveDataPoint() {
        delay();
        waitWhile(saveDataPoint, not(Condition.visible)).click();
        waitWhile($(By.cssSelector(".content > table tbody tr td > table .formError")), or(" empty or not visible", Condition.empty, not(Condition.visible)));
        waitOnPage(3000);
        return this;
    }

    public EditDataSourceWithPointListPage editDataSource() {
        delay();
        acceptAfterClick(editDataSource);
        acceptAlertOnPage();
        printCurrentUrl();
        return editDataSourceWithPointListPage;
    }

    public DataPointPropertiesPage addEventDetector() {
        delay();
        addEventDetector.click();
        return this;
    }

    public DataPointPropertiesPage addTextRendererRange() {
        delay();
        $(ADD_RANGE_IMG_BY).click();
        return this;
    }

    public DataPointPropertiesPage deleteEventDetector(EventDetectorCriteria criteria) {
        delay();
        ElementsCollection elements = waitWhile(eventDetectorTable, not(Condition.visible)).$$(By.tagName("tbody"));
        for (SelenideElement element: elements) {
            if(element.is(not(Condition.matchesText("Event detectors")))) {
                ElementsCollection inputs = element.$$(By.tagName("input"));
                for (SelenideElement input : inputs) {
                    String value = input.getValue();
                    if (value != null && value.equals(criteria.getIdentifier().getValue())) {
                        element.$(By.cssSelector("img[onclick*='deleteDetector']")).click();
                        break;
                    }
                }
            }
        }
        return this;
    }

    public DataPointPropertiesPage setDataPointName(DataPointIdentifier identifier) {
        setValue(dataPointNameInput, identifier.getValue());
        return this;
    }

    public String getDataPointName() {
        return getValue(dataPointNameInput);
    }

    public DataPointPropertiesPage setChartRendererLimit(int limit) {
        setValue(CHART_RENDERER_LIMIT_INPUT_BY, String.valueOf(limit));
        return this;
    }

    public String getChartRendererLimit() {
        return getValue(CHART_RENDERER_LIMIT_INPUT_BY);
    }

    public DataPointPropertiesPage setTextRendererFrom(String from) {
        setValue(TEXT_RENDERER_FROM_INPUT_BY, from);
        return this;
    }

    public String getTextRendererFrom() {
        return getValue(TEXT_RENDERER_FROM_INPUT_BY);
    }

    public DataPointPropertiesPage setTextRendererTo(String to) {
        setValue(TEXT_RENDERER_TO_INPUT_BY, to);
        return this;
    }

    public String getTextRendererTo() {
        return getValue(TEXT_RENDERER_TO_INPUT_BY);
    }

    public DataPointPropertiesPage setChartRendererPeriod(int period) {
        executeJQuery(val(CHART_RENDERER_PERIOD_INPUT, String.valueOf(period)));
        return this;
    }

    public int getChartRendererPeriod() {
        return Integer.parseInt(executeJQueryString(val(CHART_RENDERER_PERIOD_INPUT)));
    }

    public DataPointPropertiesPage selectChartRendererPeriodType(PeriodType periodType) {
        executeJQuery(attrSelected(CHART_RENDERER_PERIOD_TYPE_SELECT, periodType.getName()));
        return this;
    }

    public PeriodType getChartRendererPeriodType() {
        return PeriodType.getType(executeJQueryString(val(CHART_RENDERER_PERIOD_TYPE_SELECT)));
    }


    public DataPointPropertiesPage setTextRendererText(String text) {
        executeJQuery(val(TEXT_RENDERER_TEXT_INPUT, text));
        return this;
    }

    public String getTextRendererText() {
        return executeJQueryString(val(TEXT_RENDERER_TEXT_INPUT));
    }

    public String getTextRendererTimeConversionExponent() {
        return getValue(TIME_CONVERSION_EXPONENT_INPUT_BY);
    }

    public DataPointPropertiesPage setTextRendererTimeConversionExponent(int conversionExponent) {
        setValue(TIME_CONVERSION_EXPONENT_INPUT_BY, String.valueOf(conversionExponent));
        return this;
    }

    public DataPointPropertiesPage setLoggingDefaultCacheSize(int defaultCacheSize) {
        setValue(defaultCacheSizeInput, String.valueOf(defaultCacheSize));
        return this;
    }

    public int getLoggingDefaultCacheSize() {
        return Integer.parseInt(getValue(defaultCacheSizeInput));
    }

    public DataPointPropertiesPage clearCache() {
        delay();
        clearCacheButton.click();
        return this;
    }

    public DataPointPropertiesPage setLoggingTolerance(String tolerance) {
        setValue(TOLERANCE_INPUT_BY, tolerance);
        return this;
    }

    public String getLoggingTolerance() {
        return getValue(TOLERANCE_INPUT_BY);
    }

    public DataPointPropertiesPage setColour(Color color) {
        String code = color.getCode();
        executeJs(DojoScripts.SET_COLOR.getScript(code));
        return this;
    }

    public DataPointPropertiesPage setLoggingDiscardExtremeValues(boolean discardExtremeValues) {
        click(DISCARD_EXTREME_VALUES_CHECKBOX_BY, discardExtremeValues);
        return this;
    }

    public boolean isLoggingDiscardExtremeValues() {
        return Boolean.parseBoolean(getSelected(DISCARD_EXTREME_VALUES_CHECKBOX_BY));
    }

    public DataPointPropertiesPage setChartRendererIncludeSum(boolean includeSum) {
        click(By.cssSelector(CHART_RENDERER_INCLUDE_SUM_CHECKBOX), includeSum);
        return this;
    }

    public boolean isChartRendererIncludeSum() {
        return isChecked(By.cssSelector(CHART_RENDERER_INCLUDE_SUM_CHECKBOX));
    }

    public DictionaryObject getEngineeringUnit() {
        SelenideElement optgroup = findObject(NodeCriteria.withNode(optgroup(), option(), selected()), engineeringUnitsSelect);
        String label = optgroup.getAttribute("label");
        SelenideElement option = findObject(NodeCriteria.every(option(), selected()), optgroup);
        return EngineeringUnit.getType(label, option.getText());
    }

    public DataPointPropertiesPage selectEngineeringUnit(DictionaryObject engineeringUnit) {
        selectOption(engineeringUnitsSelect, engineeringUnit);
        return this;
    }

    public PurgeType getLoggingPurgeType() {
        return PurgeType.getType(getSelected(engineeringUnitsSelect));
    }

    public DataPointPropertiesPage selectLoggingPurgeType(PurgeType purgeType) {
        selectOption(purgeTypeSelect, purgeType);
        return this;
    }

    public TextRendererType getTextRendererType() {
        return TextRendererType.getType(getSelected(textRendererSelect));
    }

    public DataPointPropertiesPage selectTextRendererType(TextRendererType textRendererType) {
        selectOption(textRendererSelect, textRendererType);
        return this;
    }

    public ChartRendererType getChartRendererType() {
        return ChartRendererType.getType(getSelected(chartRendererSelect));
    }

    public DataPointPropertiesPage selectChartRendererType(ChartRendererType chartRendererType) {
        selectOption(chartRendererSelect, chartRendererType);
        return this;
    }

    public int getIntervalLoggingPeriod() {
        return Integer.parseInt(getValue(INTERVAL_LOGGING_PERIOD_VALUE_INPUT_BY));
    }

    public DataPointPropertiesPage setIntervalLoggingPeriod(int intervalLoggingPeriod) {
        setValue(INTERVAL_LOGGING_PERIOD_VALUE_INPUT_BY, String.valueOf(intervalLoggingPeriod));
        return this;
    }

    public IntervalLoggingPeriodType getIntervalLoggingPeriodType() {
        return IntervalLoggingPeriodType.getType(getSelected(INTERVAL_LOGGING_PERIOD_TYPE_SELECT_BY));
    }

    public DataPointPropertiesPage selectIntervalLoggingPeriodType(IntervalLoggingPeriodType intervalLoggingPeriodType) {
        selectOption(INTERVAL_LOGGING_PERIOD_TYPE_SELECT_BY, intervalLoggingPeriodType);
        return this;
    }

    public IntervalLoggingType getIntervalLoggingType() {
        return IntervalLoggingType.getType(getSelected(INTERVAL_LOGGING_TYPE_SELECT_BY));
    }

    public DataPointPropertiesPage selectIntervalLoggingType(IntervalLoggingType intervalLoggingType) {
        selectOption(INTERVAL_LOGGING_TYPE_SELECT_BY, intervalLoggingType);
        return this;
    }

    public LoggingType getLoggingType() {
        return LoggingType.getType(getSelected(loggingTypeSelect));
    }

    public DataPointPropertiesPage selectLoggingType(LoggingType loggingType) {
        selectOption(loggingTypeSelect, loggingType);
        return this;
    }

    public String getTextRendererSuffix() {
        return executeJQueryString(val(TEXT_RENDERER_SUFFIX_INPUT));
    }

    public DataPointPropertiesPage setTextRendererSuffix(String suffix) {
        executeJQuery(val(TEXT_RENDERER_SUFFIX_INPUT, suffix));
        return this;
    }

    public String getTextRendererFormat() {
        return executeJQueryString(val(TEXT_RENDERER_FORMAT_INPUT));
    }

    public DataPointPropertiesPage setTextRendererFormat(String format) {
        executeJQuery(val(TEXT_RENDERER_FORMAT_INPUT, format));
        return this;
    }

    public BigDecimal getLoggingDiscardLowLimit() {
        return new BigDecimal(getValue(DISCARD_LOW_LIMIT_INPUT_BY));
    }

    public DataPointPropertiesPage setLoggingDiscardLowLimit(String discardLowLimit) {
        setValue(DISCARD_LOW_LIMIT_INPUT_BY, discardLowLimit);
        return this;
    }

    public DataPointPropertiesPage setLoggingPurgePeriod(int purePeriod) {
        setValue(purgePeriodInput, String.valueOf(purePeriod));
        return this;
    }

    public int getLoggingPurePeriod() {
        return Integer.parseInt(getValue(purgePeriodInput));
    }

    public BigDecimal getLoggingDiscardHighLimit() {
        return new BigDecimal(getValue(DISCARD_HIGH_LIMIT_INPUT_BY));
    }

    public DataPointPropertiesPage setLoggingDiscardHighLimit(String discardHighLimit) {
        setValue(DISCARD_HIGH_LIMIT_INPUT_BY, discardHighLimit);
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

    public DataPointPropertiesPage waitOnPageWhileNotVisible(EventDetectorIdentifier eventDetectorIdentifier) {
        waitWhile(a -> !this.containsObject(a), eventDetectorIdentifier);
        return this;
    }

    public DataPointPropertiesPage waitOnEventDetectorTable() {
        delay();
        waitWhileNotVisible(eventDetectorTable);
        return this;
    }

    public String getEventDetectorTableHtml() {
        delay();
        return waitWhileNotVisible(eventDetectorTable).innerHtml();
    }

    @Override
    public DataPointPropertiesPage getPage() {
        return this;
    }

    private SelenideElement _getEventDetectorData(int position) {
        ElementsCollection collectionElement = $$(By.cssSelector(GET_EVENT_DETECTOR_DATA));
        return collectionElement.get(position);
    }
}
