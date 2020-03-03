package org.scadalts.e2e.page.impl.pages.eventhandlers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.RowCriteria;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.core.utils.RegexFactory;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.EventHandlerCriteria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findAction;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.E2eUtil.acceptAlert;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.refreshWhile;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;


public class EventHandlersPage extends MainPageObjectAbstract<EventHandlersPage> {

    @FindBy(id = "tree")
    private SelenideElement tree;

    public final static String TITLE = "Event handlers";

    private final static By SELECT_NODE_PLUS_BY = By.cssSelector("img[src*='treenode_expand_plus']");

    public EventHandlersPage(SelenideElement source) {
        super(source, TITLE);
    }

    public EditEventHandlersPage openEventHandlerCreator(EventDetectorCriteria criteria) {
        doubleClickPlus(criteria);
        RowCriteria rowCriteria = RowCriteria.criteria(criteria.getIdentifier(), Tag.span());
        findObject(rowCriteria, tree).click();
        acceptAlert();
        acceptAlert();
        return page(EditEventHandlersPage.class);
    }

    public EditEventHandlersPage openEventHandlerEditor(EventHandlerCriteria criteria) {
        doubleClickPlus(criteria.getEventDetectorCriteria());
        RowCriteria rowCriteria = RowCriteria.criteria(criteria.getIdentifier(), Tag.span());
        findObject(rowCriteria, tree).click();
        acceptAlert();
        acceptAlert();
        return page(EditEventHandlersPage.class);
    }

    public EventHandlersPage doubleClickPlus(EventDetectorCriteria criteria) {
        DataSourcePointCriteria dataSourcePointCriteria = criteria.getDataSourcePointCriteria();
        RowCriteria rowCriteria = RowCriteria.criteria(dataSourcePointCriteria.getIdentifier(), Tag.div());
        refreshWhile(findAction(rowCriteria, SELECT_NODE_PLUS_BY, tree), not(Condition.visible)).click();
        rowCriteria = RowCriteria.criteria(criteria.getIdentifier(), Tag.div());
        waitWhile(findAction(rowCriteria, SELECT_NODE_PLUS_BY, tree), not(Condition.visible)).click();
        return this;
    }

    @Override
    public boolean containsObject(CriteriaObject criteria) {
        String bodyText = getBodyHtml();
        Pattern pattern = Pattern.compile(RegexFactory.identifier(criteria.getIdentifier()));
        Matcher matcher = pattern.matcher(bodyText);
        return matcher.find();
    }

    @Override
    public EventHandlersPage getPage() {
        return this;
    }
}