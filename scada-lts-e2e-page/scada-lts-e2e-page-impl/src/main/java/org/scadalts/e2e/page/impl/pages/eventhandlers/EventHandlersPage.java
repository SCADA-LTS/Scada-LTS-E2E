package org.scadalts.e2e.page.impl.pages.eventhandlers;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.CssClass;
import org.scadalts.e2e.page.core.criterias.NodeCriteria;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.core.utils.DynamicElementUtil;
import org.scadalts.e2e.page.core.utils.RegexFactory;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.EventHandlerCriteria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.utils.E2eUtil.acceptAlert;


public class EventHandlersPage extends MainPageObjectAbstract<EventHandlersPage> {

    @FindBy(css= "div[dojoattachpoint='containerNode']")
    private SelenideElement tree;

    public final static String TITLE = "Event handlers";

    private final static By SELECT_NODE_PLUS_BY = By.cssSelector("img[src*='treenode_expand_plus']");

    public EventHandlersPage(SelenideElement source) {
        super(source, TITLE);
    }

    public EditEventHandlersPage openEventHandlerCreator(EventDetectorCriteria criteria) {
        NodeCriteria nodeCriteria = NodeCriteria.criteria(criteria.getDataSourcePointCriteria().getIdentifier(), Tag.div(), new CssClass("dojoTreeNode"));
        NodeCriteria nodeCriteria2 = NodeCriteria.criteria(criteria.getIdentifier(), Tag.div());
        DynamicElementUtil.findActionInNodeInTree(tree, SELECT_NODE_PLUS_BY, nodeCriteria,nodeCriteria2).click();
        acceptAlert();
        acceptAlert();
        return page(new EditEventHandlersPage(this));
    }

    public EditEventHandlersPage openEventHandlerEditor(EventHandlerCriteria criteria) {
        NodeCriteria nodeCriteria = NodeCriteria.criteria(criteria.getEventDetectorCriteria()
                .getDataSourcePointCriteria().getIdentifier(), Tag.div(), new CssClass("dojoTreeNode"));
        NodeCriteria nodeCriteria2 = NodeCriteria.criteria(criteria.getEventDetectorCriteria().getIdentifier(), Tag.div());
        NodeCriteria nodeCriteria3 = NodeCriteria.criteria(criteria.getIdentifier(), Tag.span());
        DynamicElementUtil.findActionInNodeInTree(tree,SELECT_NODE_PLUS_BY,nodeCriteria,nodeCriteria2,nodeCriteria3).click();
        acceptAlert();
        acceptAlert();
        return page(new EditEventHandlersPage(this));
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