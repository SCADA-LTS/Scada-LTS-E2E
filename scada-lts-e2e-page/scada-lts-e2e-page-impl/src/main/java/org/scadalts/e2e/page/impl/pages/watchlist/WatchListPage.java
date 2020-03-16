package org.scadalts.e2e.page.impl.pages.watchlist;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
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

    public WatchListPage addToWatchList(DataSourcePointIdentifier identifier) {
        _findActionInSpan(identifier, SELECTOR_ACTION_ADD_TO_WATCH_LIST_BY).click();
        return this;
    }

    public String getWatchListText() {
        delay();
        return waitWhile(watchListTable, not(Condition.visible)).getText();
    }

    public boolean isVisibleWatchList() {
        delay();
        waitWhile(a -> watchListTable.is(not(Condition.visible)), null);
        return watchListTable.is(Condition.visible);
    }

    public WatchListPage setDataPointValue(DataSourcePointIdentifier identifier, String value) {
        waitWhile(_findActionInTBody(identifier, SELECTOR_INPUT_BY), not(Condition.exist)).setValue(value);
        return this;
    }

    public String getDataPointValue(DataSourcePointIdentifier identifier) {
        delay();
        SelenideElement value = _findActionInTBody(identifier, SELECTOR_GET_VALUE_BY);
        String textValue = value.getText();
        if(StringUtils.isBlank(textValue)) {
            value = waitWhile(value, Condition.empty);
        }
        String text = value.getText();
        return unformat(text);
    }

    public String getDataPointValue(DataSourcePointIdentifier identifier, String expectedValue) {
        delay();
        SelenideElement value = _findActionInTBody(identifier, SELECTOR_GET_VALUE_BY);
        String textValue = value.getText();
        String unformattedValue = unformat(expectedValue);
        if(StringUtils.isBlank(textValue) || !textValue.equals(unformattedValue)) {
            value = waitWhile(value, or("is not text: " + expectedValue, not(Condition.exactText(unformattedValue))));
        }
        String text = value.getText();
        return unformat(text);
    }

    public WatchListPage confirmDataPointValue(DataSourcePointIdentifier identifier) {
        _findActionInTBody(identifier, SELECTOR_CONFIRM_CHANGE_BY).click();
        return this;
    }

    public WatchListPage openDataPointValueEditor(DataSourcePointIdentifier identifier) {
        _findActionInTBody(identifier, SELECTOR_ACTION_EDIT_DATA_POINT_VALUE_BY).click();
        return this;
    }

    public DataPointDetailsPage openDataPointDetails(DataSourcePointIdentifier identifier) {
        _findActionInTBody(identifier, SELECTOR_DATA_POINT_DETAILS_BY).click();
        return page(DataPointDetailsPage.class);
    }

    public WatchListPage closeEditorDataPointValue(DataSourcePointIdentifier identifier) {
        _findActionInTBody(identifier, SELECTOR_ACTION_CLOSE_EDIT_BY).doubleClick();
        return this;
    }

    public WatchListPage deleteFromWatchList(DataSourcePointIdentifier identifier) {
        delay();
        SelenideElement selenideElement = _findActionInTBody(identifier, SELECTOR_DELETE_FROM_WATCH_LIST_BY);
        acceptAlertAfterClick(selenideElement);
        return this;
    }

    @Override
    public boolean containsObject(IdentifierObject identifier) {
        return getWatchListText().contains(identifier.getValue());
    }

    private SelenideElement _findActionInSpan(DataSourcePointIdentifier identifier, By selectAction) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(identifier, Tag.span());
        return findAction(nodeCriteria, selectAction, treeDiv);
    }

    private SelenideElement _findActionInTBody(DataSourcePointIdentifier identifier, By selectAction) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(identifier, Tag.tbody());
        return findAction(nodeCriteria, selectAction, watchListTable);
    }
}