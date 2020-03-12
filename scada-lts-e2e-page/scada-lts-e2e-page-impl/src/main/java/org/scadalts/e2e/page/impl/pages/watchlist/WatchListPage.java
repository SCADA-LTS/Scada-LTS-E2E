package org.scadalts.e2e.page.impl.pages.watchlist;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.NodeCriteria;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointDetailsPage;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.or;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.common.utils.FormatUtil.unformat;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAlertAfterClick;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findAction;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

@Log4j2
public class WatchListPage extends MainPageObjectAbstract<WatchListPage> {

    @FindBy(id = "treeDiv")
    private SelenideElement treeDiv;

    @FindBy(id = "watchListTable")
    private SelenideElement watchListTable;

    public static final String TITLE = "Watch list";

    public WatchListPage(SelenideElement source) {
        super(source, TITLE);
    }

    private static final By SELECTOR_ACTION_ADD_TO_WATCH_LIST_BY = By.cssSelector("span[class='dojoTreeNodeLabelTitle']");
    private static final By SELECTOR_ACTION_EDIT_DATA_POINT_VALUE_BY = By.cssSelector("td[onclick*='showChange']");
    private static final By SELECTOR_ACTION_CLOSE_EDIT_BY = By.cssSelector("div[ondblclick='hideLayer(this);']");
    private static final By SELECTOR_INPUT_BY = By.cssSelector("input[id*='txtChange']");
    private static final By SELECTOR_CONFIRM_CHANGE_BY = By.cssSelector("a[onclick*='setPoint']");
    private static final By SELECTOR_GET_VALUE_BY = By.cssSelector("td[id*='Value']");
    private static final By SELECTOR_DATA_POINT_DETAILS_BY = By.cssSelector("img[src='images/icon_comp.png']");
    private static final By SELECTOR_DELETE_FROM_WATCH_LIST_BY = By.cssSelector("img[src='images/bullet_delete.png']");

    @Override
    public WatchListPage getPage() {
        return this;
    }

    public WatchListPage addToWatchList(DataSourcePointCriteria criteria) {
        _findActionInSpan(criteria, SELECTOR_ACTION_ADD_TO_WATCH_LIST_BY).click();
        return this;
    }

    public String getWatchListText() {
        delay();
        return waitWhile(watchListTable, not(Condition.visible)).getText();
    }

    public boolean isVisibleWatchList() {
        delay();
        return watchListTable.is(Condition.visible);
    }

    public WatchListPage setDataPointValue(DataSourcePointCriteria criteria, String value) {
        waitWhile(_findActionInTBody(criteria, SELECTOR_INPUT_BY), not(Condition.exist)).setValue(value);
        return this;
    }

    public String getDataPointValue(DataSourcePointCriteria criteria) {
        delay();
        SelenideElement value = _findActionInTBody(criteria, SELECTOR_GET_VALUE_BY);
        String textValue = value.getText();
        if(StringUtils.isBlank(textValue)) {
            value = waitWhile(value, Condition.empty);
        }
        String text = value.getText();
        return unformat(text);
    }

    public String getDataPointValue(DataSourcePointCriteria criteria, String expectedValue) {
        delay();
        SelenideElement value = _findActionInTBody(criteria, SELECTOR_GET_VALUE_BY);
        String textValue = value.getText();
        String unformattedValue = unformat(expectedValue);
        if(StringUtils.isBlank(textValue) || !textValue.equals(unformattedValue)) {
            value = waitWhile(value, or("is not text: " + expectedValue, not(Condition.exactText(unformattedValue))));
        }
        String text = value.getText();
        return unformat(text);
    }

    public WatchListPage confirmDataPointValue(DataSourcePointCriteria criteria) {
        _findActionInTBody(criteria, SELECTOR_CONFIRM_CHANGE_BY).click();
        return this;
    }

    public WatchListPage openDataPointValueEditor(DataSourcePointCriteria criteria) {
        _findActionInTBody(criteria, SELECTOR_ACTION_EDIT_DATA_POINT_VALUE_BY).click();
        return this;
    }

    public DataPointDetailsPage openDataPointDetails(DataSourcePointCriteria criteria) {
        _findActionInTBody(criteria, SELECTOR_DATA_POINT_DETAILS_BY).click();
        return page(DataPointDetailsPage.class);
    }

    public WatchListPage closeEditorDataPointValue(DataSourcePointCriteria criteria) {
        _findActionInTBody(criteria, SELECTOR_ACTION_CLOSE_EDIT_BY).doubleClick();
        return this;
    }

    public WatchListPage deleteFromWatchList(DataSourcePointCriteria criteria) {
        delay();
        SelenideElement selenideElement = _findActionInTBody(criteria, SELECTOR_DELETE_FROM_WATCH_LIST_BY);
        acceptAlertAfterClick(selenideElement);
        return this;
    }

    @Override
    public boolean containsObject(CriteriaObject criteria) {
        return getWatchListText().contains(criteria.getIdentifier().getValue());
    }

    private SelenideElement _findActionInSpan(DataSourcePointCriteria criteria, By selectAction) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(criteria.getIdentifier(), criteria.getType(), Tag.span());
        return findAction(nodeCriteria, selectAction, treeDiv);
    }

    private SelenideElement _findActionInTBody(DataSourcePointCriteria criteria, By selectAction) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(criteria.getIdentifier(), criteria.getType(), Tag.tbody());
        return findAction(nodeCriteria, selectAction, watchListTable);
    }
}