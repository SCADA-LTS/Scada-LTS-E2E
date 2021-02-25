package org.scadalts.e2e.page.core.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.criterias.ActionCriteria;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.pages.MainPageObject;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
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

    public static <T extends MainPageObject<T>> SelenideElement findObjectInNodeInTree(MainPageObject<T> page,
                                                                                       SelenideElement source,
                                                                                       By selectAction,
                                                                                       NodeCriteria... nodeCriterias) {
        if(nodeCriterias.length == 0)
            throw new IllegalArgumentException();
        return _findObjectInNodeInTreeReopen(page, source, selectAction, nodeCriterias);
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

    private static ElementsCollection _findObjects(NodeCriteria criteria, SelenideElement source) {
        String xpath = criteria.getXpath();
        _toLog(criteria, source);

        ElementsCollection elements = waitWhile(source, not(Condition.visible))
                .$$(By.xpath(xpath));
        logger.debug("elements: {}", elements);
        return elements;
    }

    private static void _toLog(NodeCriteria nodeCriteria, SelenideElement source) {
        logger.info("xpath: {}, in: {}", nodeCriteria.getXpath(), source.getSearchCriteria());
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
            SelenideElement element = objectWithActions.$(criteria.getSelectAction());
            if(element.is(Condition.visible))
                elements.add(element);
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
        _toLog(criteria, source);
        SelenideElement selenideElement = source.$(By.xpath(xpath));
        return selenideElement;
    }

    private static ElementsCollection _findObjectsWithActions(NodeCriteria criteria, SelenideElement source) {
        String xpath = criteria.getXpath();
        _toLog(criteria, source);
        ElementsCollection selenideElement = source.$$(By.xpath(xpath));
        return selenideElement;
    }

    private static SelenideElement _prepareSource(NodeCriteria criteria, SelenideElement source) {
        return $(By.xpath(criteria.getXpath())).is(Condition.visible) ? source : waitWhile(source, not(Condition.visible));
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
        SelenideElement element = reopenWaitWhile(page, findObject(nodeCriterias[0], source), not(Condition.visible));
        waitWhile(element.$(selectAction), not(Condition.visible)).click();
        for (NodeCriteria nodeCriteria : nodeCriterias) {
            if(!nodeCriterias[0].equals(nodeCriteria)) {
                element = waitWhile(findObject(nodeCriteria, element), not(Condition.visible));
                if(!nodeCriterias[nodeCriterias.length - 1].equals(nodeCriteria)) {
                    waitWhile(element.$(selectAction), not(Condition.visible)).click();
                }
            }
        }
        return element;
    }

    private static <T extends MainPageObject<T>> SelenideElement _findObjectInNodeInTreeReopen(MainPageObject<T> page,
                                                                                               SelenideElement source,
                                                                                               By selectAction,
                                                                                               NodeCriteria[] nodeCriterias) {
        SelenideElement element = reopenWaitWhile(page, findObject(nodeCriterias[0], source), not(Condition.visible));
        waitWhile(element.$(selectAction), not(Condition.visible)).click();
        for (NodeCriteria nodeCriteria : nodeCriterias) {
            if(!nodeCriterias[0].equals(nodeCriteria)) {
                element = findObject(nodeCriteria, element);
                if(waitWhile(element, not(Condition.visible)).is(Condition.visible)) {
                    if (waitWhile(element.$(selectAction), not(Condition.visible)).is(Condition.visible)) {
                        element.$(selectAction).click();
                    }
                } else {
                    return element;
                }
            }
        }
        return element;
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
        SelenideElement elementParent = reopenWaitWhile(page, findObject(nodeCriterias[0], source), not(Condition.visible));
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
