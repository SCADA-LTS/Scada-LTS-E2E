package org.scadalts.e2e.page.core.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.criterias.ActionCriteria;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.NodeCriteria;
import org.scadalts.e2e.page.core.pages.MainPageObject;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.not;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.*;

@Log4j2
public abstract class DynamicElementUtil {

    public static SelenideElement findAction(NodeCriteria criteria, By selectAction, SelenideElement source) {
        ActionCriteria actionCriteria = new ActionCriteria(criteria, selectAction);
        return _findAction(actionCriteria,source);
    }

    public static SelenideElement findObject(NodeCriteria criteria, SelenideElement source) {
        return _findObject(criteria, source);
    }

    public static SelenideElement findActionInNodeInTree(SelenideElement source, By selectAction,
                                                         NodeCriteria... nodeCriterias) {
        if(nodeCriterias.length == 0)
            throw new IllegalArgumentException();
        return _findActionInNodeInTree(source, selectAction, nodeCriterias);
    }

    public static <T extends MainPageObject<T>> SelenideElement findActionInNodeInTree(MainPageObject<T> page,
                                                                                       SelenideElement source,
                                                                                       By selectAction,
                                                                                       NodeCriteria... nodeCriterias) {
        if(nodeCriterias.length == 0)
            throw new IllegalArgumentException();
        return _findActionInNodeInTreeReopen(page, source, selectAction, nodeCriterias);
    }

    public static SelenideElement findNodeClickableInTree(SelenideElement source, NodeCriteria... nodeCriterias)  {
        if(nodeCriterias.length == 0)
            throw new IllegalArgumentException();
        return _findNodeClickableInTree(source, nodeCriterias);
    }

    public static <T extends MainPageObject<T>> SelenideElement findNodeClickableInTree(MainPageObject<T> page,
                                                                                        SelenideElement source,
                                                                                        NodeCriteria... nodeCriterias)  {
        if(nodeCriterias.length == 0)
            throw new IllegalArgumentException();
        return _findNodeClickableInTreeReopen(page, source, nodeCriterias);
    }

    public static ElementsCollection findObjects(NodeCriteria nodeCriteria, SelenideElement source) {
        return _findObjects(nodeCriteria, source);
    }

    public static List<SelenideElement> findActions(NodeCriteria criteria, By selectAction, SelenideElement source) {
        ActionCriteria actionCriteria = new ActionCriteria(criteria, selectAction);
        return _findActions(actionCriteria,source);
    }

    private static ElementsCollection _findObjects(NodeCriteria nodeCriteria, SelenideElement source) {
        String xpath = nodeCriteria.getXpath();
        logger.info("xpath: {}", xpath);

        ElementsCollection elements = waitWhile(source, not(Condition.visible))
                .$$(By.xpath(xpath));
        logger.debug("elements: {}", elements);
        return elements;
    }

    private static SelenideElement _findObject(NodeCriteria criteria, SelenideElement source) {
        SelenideElement reloadedElement = _prepareSource(criteria, source);
        return _findObjectWithActions(criteria, reloadedElement);
    }

    private static SelenideElement _findAction(ActionCriteria criteria, SelenideElement source) {
        SelenideElement reloadedElement = _prepareSource(criteria.getCriteria(), source);
        SelenideElement objectWithActions = _findObjectWithActions(criteria, reloadedElement);
        return objectWithActions.$(criteria.getSelectAction());
    }

    private static List<SelenideElement> _findActions(ActionCriteria criteria, SelenideElement source) {
        SelenideElement reloadedElement = _prepareSource(criteria.getCriteria(), source);
        ElementsCollection objectsWithActions = _findObjectsWithActions(criteria, reloadedElement);
        List<SelenideElement> elements = new ArrayList<>();
        for (SelenideElement objectWithActions :
                objectsWithActions) {
            elements.add(objectWithActions.$(criteria.getSelectAction()));
        }
        return elements;
    }

    private static SelenideElement _findObjectWithActions(ActionCriteria criteria, SelenideElement source) {
        return _findObjectWithActions(criteria.getCriteria(), source);
    }

    private static ElementsCollection _findObjectsWithActions(ActionCriteria criteria, SelenideElement source) {
        return _findObjectsWithActions(criteria.getCriteria(), source);
    }

    private static SelenideElement _findObjectWithActions(NodeCriteria criteria, SelenideElement source) {
            String xpath = criteria.getXpath();
        logger.info("xpath: {}", xpath);
        SelenideElement selenideElement = source.$(By.xpath(xpath));
        return selenideElement;
    }

    private static ElementsCollection _findObjectsWithActions(NodeCriteria criteria, SelenideElement source) {
        String xpath = criteria.getXpath();
        logger.info("xpath: {}", xpath);
        ElementsCollection selenideElement = source.$$(By.xpath(xpath));
        return selenideElement;
    }

    private static SelenideElement _prepareSource(CriteriaObject criteria, SelenideElement source) {
        String text = source.getText();
        String objectName = criteria.getIdentifier().getValue();
        return text.contains(objectName) ? source : waitWhile(source, not(Condition.visible));
    }

    private static SelenideElement _findActionInNodeInTree(SelenideElement source, By selectAction, NodeCriteria[] nodeCriterias) {
        SelenideElement elementParent = refreshWhile(findObject(nodeCriterias[0], source), not(Condition.visible));
        waitWhile(elementParent.$(selectAction), not(Condition.visible)).click();
        for (NodeCriteria nodeCriteria : nodeCriterias) {
            if(!nodeCriterias[0].equals(nodeCriteria)) {
                elementParent = waitWhile(findObject(nodeCriteria, elementParent), not(Condition.visible));
                if(!nodeCriterias[nodeCriterias.length - 1].equals(nodeCriteria)) {
                    waitWhile(elementParent.$(selectAction), not(Condition.visible)).click();
                }
            }
        }
        return elementParent;
    }

    private static <T extends MainPageObject<T>> SelenideElement _findActionInNodeInTreeReopen(MainPageObject<T> page,
                                                                                                 SelenideElement source,
                                                                                                 By selectAction,
                                                                                                 NodeCriteria[] nodeCriterias) {
        SelenideElement elementParent = reopenWhile(page, findObject(nodeCriterias[0], source), not(Condition.visible));
        waitWhile(elementParent.$(selectAction), not(Condition.visible)).click();
        for (NodeCriteria nodeCriteria : nodeCriterias) {
            if(!nodeCriterias[0].equals(nodeCriteria)) {
                elementParent = waitWhile(findObject(nodeCriteria, elementParent), not(Condition.visible));
                if(!nodeCriterias[nodeCriterias.length - 1].equals(nodeCriteria)) {
                    waitWhile(elementParent.$(selectAction), not(Condition.visible)).click();
                }
            }
        }
        return elementParent;
    }

    private static SelenideElement _findNodeClickableInTree(SelenideElement source, NodeCriteria[] nodeCriterias) {
        SelenideElement elementParent = refreshWhile(findObject(nodeCriterias[0], source), not(Condition.visible));
        elementParent.click();
        for (NodeCriteria nodeCriteria : nodeCriterias) {
            if(!nodeCriterias[0].equals(nodeCriteria)) {
                elementParent = waitWhile(findObject(nodeCriteria, elementParent), not(Condition.visible));
                if(!nodeCriterias[nodeCriterias.length - 1].equals(nodeCriteria)) {
                    elementParent.click();
                }
            }
        }
        return elementParent;
    }

    private static <T extends MainPageObject<T>> SelenideElement _findNodeClickableInTreeReopen(MainPageObject<T> page,
                                                                  SelenideElement source,
                                                                  NodeCriteria[] nodeCriterias) {
        SelenideElement elementParent = reopenWhile(page, findObject(nodeCriterias[0], source), not(Condition.visible));
        elementParent.click();
        for (NodeCriteria nodeCriteria : nodeCriterias) {
            if(!nodeCriterias[0].equals(nodeCriteria)) {
                elementParent = waitWhile(findObject(nodeCriteria, elementParent), not(Condition.visible));
                if(!nodeCriterias[nodeCriterias.length - 1].equals(nodeCriteria)) {
                    elementParent.click();
                }
            }
        }
        return elementParent;
    }
}
