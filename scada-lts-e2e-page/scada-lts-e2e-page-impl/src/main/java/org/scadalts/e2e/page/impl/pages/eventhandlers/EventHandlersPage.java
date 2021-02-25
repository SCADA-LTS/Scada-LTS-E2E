package org.scadalts.e2e.page.impl.pages.eventhandlers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.EventHandlerCriteria;

import java.util.function.Predicate;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAfterClick;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.*;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.reopenWhile;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;


public class EventHandlersPage extends MainPageObjectAbstract<EventHandlersPage> {

    @FindBy(css= "div[dojoattachpoint='containerNode']")
    private SelenideElement tree;

    @FindBy(css = "a[href='event_handlers.shtm']")
    private SelenideElement source;

    public final static String TITLE = "Event handlers";

    private final static By SELECT_NODE_PLUS_BY = By.cssSelector("img[src*='treenode_expand_plus']");

    public EventHandlersPage() {
        super(TITLE, "/event_handlers.shtm");
    }

    public EditEventHandlersPage openEventHandlerCreator(EventDetectorCriteria criteria) {
        delay();
        SelenideElement selenideElement = _findActionInTree(criteria);
        acceptAfterClick(selenideElement);
        printCurrentUrl();
        return page(new EditEventHandlersPage(this));
    }

    @Override
    public EventHandlersPage waitForCompleteLoad() {
        waitWhile(tree, not(Condition.visible));
        return this;
    }

    public EditEventHandlersPage openEventHandlerEditor(EventHandlerCriteria criteria) {
        delay();
        SelenideElement selenideElement = _findActionInTree(criteria);
        acceptAfterClick(selenideElement);
        printCurrentUrl();
        return page(new EditEventHandlersPage(this));
    }

    @Override
    public boolean containsObject(IdentifierObject identifier) {
        delay();
        return reopenWhile(this,
                findObject(identifier.getNodeCriteria(), tree), not(Condition.exist)).is(Condition.exist);
    }

    @Override
    public boolean containsObject(CriteriaObject criteria) {
        delay();
        return reopenWhile(this,
                _findObjectInTree(criteria), not(Condition.exist)).is(Condition.exist);
    }

    @Override
    public EventHandlersPage getPage() {
        return this;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }

    private SelenideElement _findActionInTree(CriteriaObject criteria) {
        return findActionInNodeInTree(this, tree, SELECT_NODE_PLUS_BY, criteria.getNodeCriteria());
    }

    private SelenideElement _findObjectInTree(CriteriaObject criteria) {
        return findObjectInNodeInTree(this, tree, SELECT_NODE_PLUS_BY, criteria.getNodeCriteria());
    }
}