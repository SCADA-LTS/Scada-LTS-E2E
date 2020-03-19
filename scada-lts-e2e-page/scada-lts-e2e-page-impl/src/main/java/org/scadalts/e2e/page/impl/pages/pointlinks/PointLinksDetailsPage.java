package org.scadalts.e2e.page.impl.pages.pointlinks;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.dicts.EventType;

import static com.codeborne.selenide.Condition.not;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

public class PointLinksDetailsPage extends PageObjectAbstract<PointLinksDetailsPage> {

    @FindBy(id = "deletePointLinkImg")
    private SelenideElement deletePointLink;

    @FindBy(id = "sourcePointId")
    private SelenideElement sourcePointId;

    @FindBy(id = "targetPointId")
    private SelenideElement targetPointId;

    @FindBy(id = "script")
    private SelenideElement script;

    @FindBy(id = "event")
    private SelenideElement event;

    @FindBy(id = "disabled")
    private SelenideElement disabled;

    @FindBy(css = "img[onclick*='savePointLink']")
    private SelenideElement savePointLink;

    private final PointLinksPage pointLinksPage;

    public final static String TITLE = "";

    public PointLinksDetailsPage(PointLinksPage pointLinksPage) {
        super(TITLE);
        this.pointLinksPage = pointLinksPage;
    }

    public PointLinksDetailsPage setPoints(PointLinkCriteria criteria) {
        delay();
        IdentifierObject source = criteria.getSource().getIdentifier();
        IdentifierObject target = criteria.getTarget().getIdentifier();

        waitWhile(targetPointId, not(Condition.visible)).selectOption(target.getValue());
        waitWhile(sourcePointId, not(Condition.visible)).selectOption(source.getValue());

        return this;
    }

    public PointLinksDetailsPage setScript(Script script) {
        delay();
        this.script.clear();
        this.script.setValue(script.getContent());
        return this;
    }

    public PointLinksDetailsPage setEventType(EventType eventType) {
        delay();
        this.event.selectOption(eventType.getName());
        return this;
    }

    public PointLinksDetailsPage disabled() {
        delay();
        this.disabled.click();
        return this;
    }

    public PointLinksPage deletePointLink() {
        delay();
        deletePointLink.click();
        return pointLinksPage;
    }

    public PointLinksPage savePointLink() {
        delay();
        savePointLink.click();
        return pointLinksPage;
    }

    public String getSourcePointIdText() {
        delay();
        return sourcePointId.getText();
    }

    public String getTargetPointIdText() {
        delay();
        return targetPointId.getText();
    }

    public String getScript() {
        delay();
        return script.getValue();
    }

    public EventType getEventType() {
        delay();
        return EventType.getType(event.getText());
    }

    public boolean isDisabled() {
        delay();
        return disabled.isEnabled();
    }

    @Override
    public PointLinksDetailsPage getPage() {
        return this;
    }
}
