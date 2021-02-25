package org.scadalts.e2e.page.impl.pages.watchlist;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.common.core.utils.VariationUnit;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.javascripts.JQueryScripts;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.WatchListIdentifier;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointDetailsPage;

import java.util.List;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.or;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.common.core.utils.FormatUtil.unformat;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAfterClick;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findAction;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.refreshWhile;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

@Log4j2
public class WatchListPage extends MainPageObjectAbstract<WatchListPage> {

    @FindBy(id = "treeDiv")
    private SelenideElement treeDiv;

    @FindBy(id = "watchListTable")
    private SelenideElement watchListTable;

    @FindBy(id = "watchListDiv")
    private SelenideElement watchListDiv;

    @FindBy(css = "img[src='images/report_add.png']")
    private SelenideElement addReportNewWatchListButton;

    @FindBy(css = "img[src*='images/delete.png']")
    private SelenideElement deleteWatchListButton;

    @FindBy(css = "img[src='images/pencil.png']")
    private SelenideElement editWatchListButton;

    @FindBy(css = "img[src='images/copy.png']")
    private SelenideElement copyWatchListButton;

    @FindBy(id = "watchListSelect")
    private SelenideElement watchListSelect;

    @FindBy(css = "a[href='watch_list.shtm']")
    private SelenideElement source;

    private static final String URL_REF = "/watch_list.shtm";

    public static final String TITLE = "Watch list";

    public WatchListPage() {
        super(TITLE, URL_REF);
    }

    private static final By SELECTOR_ACTION_ADD_TO_WATCH_LIST_BY = By.cssSelector("span[class='dojoTreeNodeLabelTitle']");
    private static final By SELECTOR_ACTION_EDIT_DATA_POINT_VALUE_BY = By.cssSelector("td[onclick*='showChange']");
    private static final By SELECTOR_ACTION_CLOSE_EDIT_BY = By.cssSelector("div[ondblclick='hideLayer(this);']");
    private static final By SELECTOR_INPUT_BY = By.cssSelector("input[id*='txtChange']");
    private static final By SELECTOR_CONFIRM_CHANGE_BY = By.cssSelector("a[onclick*='setPoint']");
    private static final By SELECTOR_GET_VALUE_BY = By.cssSelector("td[id*='Value']");
    private static final By SELECTOR_DATA_POINT_DETAILS_BY = By.cssSelector("img[src='images/icon_comp.png']");
    private static final By SELECTOR_DELETE_FROM_WATCH_LIST_BY = By.cssSelector("img[src='images/bullet_delete.png']");
    private static final By NEW_WATCH_LIST_NAME_TEXT_BY = By.id("newWatchListName");
    private static final By SAVE_NEW_WATCH_LIST_NAME_BUTTON_BY = By.cssSelector("a[onclick*='saveWatchListName']");

    private static String ADD_NEW_WATCH_LIST = "img[src='images/add.png']";

    @Override
    public WatchListPage getPage() {
        return this;
    }

    public WatchListPage addWatchList(WatchListCriteria criteria) {
        delay();
        executeJQuery(JQueryScripts.click(ADD_NEW_WATCH_LIST));
        delay();
        waitWhile(editWatchListButton, not(Condition.visible)).click();
        waitWhile($(NEW_WATCH_LIST_NAME_TEXT_BY), not(Condition.visible))
                .setValue(criteria.getIdentifier().getValue());
        waitWhile($(SAVE_NEW_WATCH_LIST_NAME_BUTTON_BY), not(Condition.visible)).click();
        waitWhile(() -> !containsObject(criteria.getIdentifier()));
        return this;
    }

    public WatchListPage selectWatchList(WatchListIdentifier watchListName) {
        delay();
        waitWhile(watchListSelect, not(Condition.visible))
                .selectOption(watchListName.getValue());
        return this;
    }

    public boolean isSelectedWatchList(WatchListIdentifier identifier) {
        delay();
        String watchListName = waitWhile(watchListSelect, not(Condition.visible)).getSelectedText();
        return identifier.getValue().equals(watchListName);
    }

    public WatchListPage addDataToWatchList(DataSourcePointIdentifier identifier) {

        refreshWhile(_findActionInSpan(identifier, SELECTOR_ACTION_ADD_TO_WATCH_LIST_BY),
                not(Condition.visible)).click();
        waitWhile(() -> !isVisibleWatchListUnit(identifier));
        return this;
    }

    public String getWatchListText() {
        delay();
        return waitWhile(watchListDiv, not(Condition.visible)).getText();
    }

    public boolean isVisibleWatchList(WatchListIdentifier identifier) {
        delay();
        return containsObject(identifier);
    }

    public boolean isVisibleWatchListUnit(DataSourcePointIdentifier identifier) {
        return getWatchListText().contains(identifier.getValue());
    }

    public boolean isVisibleWatchListTable() {
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
        printCurrentUrl();
        return page(DataPointDetailsPage.class);
    }

    public WatchListPage closeEditorDataPointValue(DataSourcePointIdentifier identifier) {
        _findActionInTBody(identifier, SELECTOR_ACTION_CLOSE_EDIT_BY).doubleClick();
        return this;
    }

    public WatchListPage deleteFromWatchList(DataSourcePointIdentifier identifier) {
        delay();
        SelenideElement selenideElement = _findActionInTBody(identifier, SELECTOR_DELETE_FROM_WATCH_LIST_BY);
        acceptAfterClick(selenideElement);
        return this;
    }

    public WatchListPage deleteWatchList(WatchListIdentifier identifier) {
        delay();
        if(isVisibleWatchList(identifier)) {
            selectWatchList(identifier);
            if(isSelectedWatchList(identifier)) {
                waitWhile(deleteWatchListButton, not(Condition.visible)).click();
            }
        }
        return this;
    }

    public WatchListPage setValue(DataSourcePointIdentifier identifier, String value) {
        return openDataPointValueEditor(identifier)
                .setDataPointValue(identifier,  value)
                .confirmDataPointValue(identifier)
                .closeEditorDataPointValue(identifier);
    }

    public WatchListPage setValueWithoutCloseEditor(DataSourcePointIdentifier identifier, String value) {
        return openDataPointValueEditor(identifier)
                .setDataPointValue(identifier,  value)
                .confirmDataPointValue(identifier);
    }

    public <T> void setSequenceInts(DataSourcePointIdentifier identifier, VariationUnit<T> values) {
        setSequenceInts(identifier, values.getVariation());
    }

    public <T> void setSequenceInts(DataSourcePointIdentifier identifier, List<T> values) {
        for(T value: values) {
            setValue(identifier, String.valueOf(value));
        }
    }

    @Override
    public boolean containsObject(IdentifierObject identifier) {
        ElementsCollection watchLists = waitWhile(watchListSelect, not(Condition.visible)).$$(By.tagName("option"));
        return watchLists.stream().anyMatch(a -> a.getText().equalsIgnoreCase(identifier.getValue()));
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
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