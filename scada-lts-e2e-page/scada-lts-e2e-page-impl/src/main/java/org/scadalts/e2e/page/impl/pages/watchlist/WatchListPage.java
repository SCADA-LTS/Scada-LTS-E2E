package org.scadalts.e2e.page.impl.pages.watchlist;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criteria.ActionCriteria;
import org.scadalts.e2e.page.core.criteria.RowCriteria;
import org.scadalts.e2e.page.core.exceptions.DynamicElementException;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criteria.WatchListCriteria;

import static com.codeborne.selenide.Condition.not;
import static org.scadalts.e2e.page.core.util.DynamicElementUtil.findAction;
import static org.scadalts.e2e.page.core.util.StabilityUtil.waitWhile;

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

    private static final By SELECTOR_ACTION_ADD_TO_WATCH_BY = By.cssSelector("span[class='dojoTreeNodeLabelTitle']");
    private static final By SELECTOR_ACTION_EDIT_DATA_POINT_VALUE_BY = By.cssSelector("td[onclick*='showChange']");
    private static final By SELECTOR_ACTION_CLOSE_EDIT_BY = By.cssSelector("div[ondblclick='hideLayer(this);']");
    private static final By SELECTOR_INPUT_BY = By.cssSelector("input[id*='txtChange']");
    private static final By SELECTOR_CONFIRM_CHANGE_BY = By.cssSelector("a[onclick*='setPoint']");
    private static final By SELECTOR_GET_VALUE_BY = By.cssSelector("td[id*='Value']");

    @Override
    public WatchListPage getPage() {
        return this;
    }

    public WatchListPage addToWatchList(WatchListCriteria criteria) {
        _findActionInSpan(criteria, SELECTOR_ACTION_ADD_TO_WATCH_BY).click();
        return this;
    }

    public WatchListPage setDataPointValue(WatchListCriteria criteria, String value) {
        waitWhile(_findActionInTBody(criteria, SELECTOR_INPUT_BY), not(Condition.exist)).sendKeys(value);
        return this;
    }

    public String getDataPointValue(WatchListCriteria criteria) {
        SelenideElement text = _findActionInTBody(criteria, SELECTOR_GET_VALUE_BY);
        String value = text.getText();
        if(ObjectUtils.isEmpty(value)) {
            text = waitWhile(text, Condition.empty);
        }
        return text.getText();
    }

    public WatchListPage confirmDataPointValue(WatchListCriteria criteria) {
        _findActionInTBody(criteria, SELECTOR_CONFIRM_CHANGE_BY).click();
        return this;
    }

    public WatchListPage openEditorDataPointValue(WatchListCriteria criteria) {
        _findActionInTBody(criteria, SELECTOR_ACTION_EDIT_DATA_POINT_VALUE_BY).click();
        return this;
    }

    public WatchListPage closeEditorDataPointValue(WatchListCriteria criteria) {
        _findActionInTBody(criteria, SELECTOR_ACTION_CLOSE_EDIT_BY).doubleClick();
        return this;
    }

    private SelenideElement _findActionInSpan(WatchListCriteria criteria, By selectAction) {
        RowCriteria rowCriteria = new RowCriteria(criteria.getIdentifier(), criteria.getType());
        ActionCriteria actionCriteria = new ActionCriteria(rowCriteria, selectAction);
        try {
            return findAction(actionCriteria, treeDiv, "span");
        } catch (DynamicElementException e) {
            throw new RuntimeException(e);
        }
    }

    private SelenideElement _findActionInTBody(WatchListCriteria criteria, By selectAction) {
        RowCriteria rowCriteria = new RowCriteria(criteria.getIdentifier(), criteria.getType());
        ActionCriteria actionCriteria = new ActionCriteria(rowCriteria, selectAction);
        try {
            return findAction(actionCriteria, watchListTable, "tbody");
        } catch (DynamicElementException e) {
            throw new RuntimeException(e);
        }
    }
}