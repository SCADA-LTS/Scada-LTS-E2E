package org.scadalts.e2e.page.impl.pages.pointlinks;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.common.core.utils.StabilityUtil;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.config.PageConfiguration;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.page;
import static org.scadalts.e2e.page.core.criterias.Tag.td;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObject;
import static org.scadalts.e2e.page.core.utils.DynamicElementUtil.findObjects;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.*;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.clazz;

@Log4j2
public class PointLinksPage extends MainPageObjectAbstract<PointLinksPage> {

    @FindBy(id = "pl-1Img")
    private SelenideElement addPointLink;

    @FindBy(id = "pointLinksTable")
    private SelenideElement pointLinksTable;

    @FindBy(css = "a[href='point_links.shtm']")
    private SelenideElement source;

    public static final String TITLE = "Point links";

    public PointLinksPage() {
        super(TITLE, "/point_links.shtm");
    }

    public PointLinksDetailsPage openPointLinkCreator() {
        delay();
        waitWhile(addPointLink, not(Condition.visible)).click();
        return page(new PointLinksDetailsPage(this));
    }

    @Override
    public PointLinksPage waitForCompleteLoad() {
        waitWhile(pointLinksTable, not(Condition.visible));
        NodeCriteria nodeCriteria = NodeCriteria.every(1, 0, td(), clazz("link"));
        waitWhile(a -> findObjects(nodeCriteria, pointLinksTable).isEmpty(),null);
        return this;
    }

    public String getPointLinksTableText() {
        delay();
        waitForCompleteLoad();
        String text = pointLinksTable.getText();
        if(StringUtils.isBlank(text))
            waitWhileNotVisible(pointLinksTable);
        return pointLinksTable.getText();
    }

    public String getPointLinksTableHtml() {
        delay();
        return pointLinksTable.innerHtml();
    }

    public PointLinksDetailsPage openPointLinkEditor(PointLinkCriteria criteria) {
        scrollDownWhile(this, a -> !containsObject(a.getIdentifier()), criteria);
        _findAction(criteria).click();
        scrollUp();
        return page(new PointLinksDetailsPage(this));
    }

    @Override
    public PointLinksPage getPage() {
        return this;
    }

    @Override
    public boolean containsObject(IdentifierObject identifier) {
        delay();
        waitForCompleteLoad();
        waitWhile(a -> findObjects(identifier.getNodeCriteria(), pointLinksTable).isEmpty(),null,
                new StabilityUtil.Timeout(PageConfiguration.timeout));
        return findObject(identifier.getNodeCriteria(), pointLinksTable).is(Condition.visible);
    }

    @Override
    public PointLinksPage waitForObject(IdentifierObject identifier) {
        delay();
        return waitForObject(identifier.getNodeCriteria());
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }

    private SelenideElement _findAction(PointLinkCriteria criteria) {
        delay();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(criteria.getSource().getIdentifier(),
                criteria.getTarget().getIdentifier(), Tag.tbody());
        return findObject(nodeCriteria, pointLinksTable);
    }
}