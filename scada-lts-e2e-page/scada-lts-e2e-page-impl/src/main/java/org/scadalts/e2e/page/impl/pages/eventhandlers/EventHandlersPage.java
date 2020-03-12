package org.scadalts.e2e.page.impl.pages.eventhandlers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.criterias.*;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.core.utils.RegexFactory;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.EventHandlerCriteria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAlertAfterClick;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findActionInNodeInTree;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;


public class EventHandlersPage extends MainPageObjectAbstract<EventHandlersPage> {

    @FindBy(css= "div[dojoattachpoint='containerNode']")
    private SelenideElement tree;

    public final static String TITLE = "Event handlers";

    private final static By SELECT_NODE_PLUS_BY = By.cssSelector("img[src*='treenode_expand_plus']");

    public EventHandlersPage(SelenideElement source) {
        super(source, TITLE);
    }

    public EditEventHandlersPage openEventHandlerCreator(EventDetectorCriteria criteria) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(criteria.getDataSourcePointCriteria().getIdentifier(), Tag.div(), new CssClass("dojoTreeNode"));
        NodeCriteria nodeCriteria2 = NodeCriteria.exactly(criteria.getIdentifier(), Tag.span());
        SelenideElement selenideElement = findActionInNodeInTree(this, tree, SELECT_NODE_PLUS_BY, nodeCriteria,nodeCriteria2);
        acceptAlertAfterClick(selenideElement);
        return page(new EditEventHandlersPage(this));
    }

    @Override
    public E2eWebElement getTarget() {
        return E2eWebElement.newInstance(tree);
    }

    public EventHandlersPage waitOnContainerNode() {
        waitWhile(tree, not(Condition.visible));
        return this;
    }

    public EditEventHandlersPage openEventHandlerEditor(EventHandlerCriteria criteria) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(criteria.getEventDetectorCriteria()
                .getDataSourcePointCriteria().getIdentifier(), Tag.div(), new CssClass("dojoTreeNode"));
        NodeCriteria nodeCriteria2 = NodeCriteria.exactly(criteria.getEventDetectorCriteria().getIdentifier(), Tag.div());
        NodeCriteria nodeCriteria3 = NodeCriteria.exactly(criteria.getIdentifier(), Tag.span());
        SelenideElement selenideElement = findActionInNodeInTree(this, tree,SELECT_NODE_PLUS_BY,nodeCriteria,nodeCriteria2,nodeCriteria3);
        acceptAlertAfterClick(selenideElement);
        return page(new EditEventHandlersPage(this));
    }

    @Override
    public boolean containsObject(CriteriaObject criteria) {
        delay();
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