package org.scadalts.e2e.page.core.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.pages.MainPageObject;
import org.scadalts.e2e.page.core.xpaths.XpathExpression;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.*;

@Log4j2
public abstract class DynamicElementUtil {

    public static SelenideElement findAction(NodeCriteria nodeCriteria, By selectAction, SelenideElement source) {
        return _findAction(nodeCriteria, selectAction, source);
    }

    public static SelenideElement findObject(NodeCriteria nodeCriteria, SelenideElement source) {
        return _findObject(nodeCriteria, source);
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
        return _findObjects(nodeCriteria.getXpath(), source);
    }

    public static List<SelenideElement> findActions(NodeCriteria nodeCriteria, By selectAction, SelenideElement source) {
        return _findActions(nodeCriteria, selectAction, source);
    }

    private static ElementsCollection _findObjects(XpathExpression xpathExpression, SelenideElement source) {
        String xpath = xpathExpression.toExecute();
        logger.info("xpath: {}", xpath);

        ElementsCollection elements = waitWhile(source, not(Condition.visible))
                .$$(By.xpath(xpath));
        logger.debug("elements: {}", elements);
        return elements;
    }

    private static SelenideElement _findObject(NodeCriteria nodeCriteria, SelenideElement source) {
        SelenideElement reloadedElement = _prepareSource(nodeCriteria.getXpath(), source);
        return _findObjectWithActions(nodeCriteria.getXpath(), reloadedElement);
    }

    private static SelenideElement _findAction(NodeCriteria nodeCriteria, By selectAction, SelenideElement source) {
        SelenideElement reloadedElement = _prepareSource(nodeCriteria.getXpath(), source);
        SelenideElement objectWithActions = _findObjectWithActions(nodeCriteria, reloadedElement);
        return objectWithActions.$(selectAction);
    }

    private static List<SelenideElement> _findActions(NodeCriteria nodeCriteria, By selectAction, SelenideElement source) {
        SelenideElement reloadedElement = _prepareSource(nodeCriteria.getXpath(), source);
        ElementsCollection objectsWithActions = _findObjectsWithActions(nodeCriteria, reloadedElement);
        List<SelenideElement> elements = new ArrayList<>();
        for (SelenideElement objectWithActions :
                objectsWithActions) {
            SelenideElement element = objectWithActions.$(selectAction);
            if(element.is(Condition.visible))
                elements.add(element);
        }
        return elements;
    }

    private static SelenideElement _findObjectWithActions(NodeCriteria nodeCriteria, SelenideElement source) {
        return _findObjectWithActions(nodeCriteria.getXpath(), source);
    }

    private static ElementsCollection _findObjectsWithActions(NodeCriteria nodeCriteria, SelenideElement source) {
        return _findObjectsWithActions(nodeCriteria.getXpath(), source);
    }

    private static SelenideElement _findObjectWithActions(XpathExpression xpathExpression, SelenideElement source) {
        String xpath = xpathExpression.toExecute();
        logger.info("xpath: {}", xpath);
        return source.$(By.xpath(xpath));
    }

    private static ElementsCollection _findObjectsWithActions(XpathExpression xpathExpression, SelenideElement source) {
        String xpath = xpathExpression.toExecute();
        logger.info("xpath: {}", xpath);
        return source.$$(By.xpath(xpath));
    }

    private static SelenideElement _prepareSource(XpathExpression xpathExpression, SelenideElement source) {
        return $(By.xpath(xpathExpression.toExecute())).is(Condition.visible) ? source
                : waitWhile(source, not(Condition.visible));
    }

    private static SelenideElement _findActionInNodeInTree(SelenideElement source, By selectAction,
                                                           NodeCriteria[] nodeCriterias) {
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
