package org.scadalts.e2e.page.impl.pages.pointlinks;


import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.criterias.IdentifierObject;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.dicts.EventType;

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
        IdentifierObject source = criteria.getSource().getIdentifier();
        IdentifierObject target = criteria.getTarget().getIdentifier();
        this.sourcePointId.selectOption(source.getValue());
        this.targetPointId.selectOption(target.getValue());
        return this;
    }

    public PointLinksDetailsPage setScript(String script) {
        this.script.clear();
        this.script.sendKeys(script);
        return this;
    }

    public PointLinksDetailsPage setEventType(EventType eventType) {
        this.event.selectOption(eventType.getName());
        return this;
    }

    public PointLinksDetailsPage disabled() {
        this.disabled.click();
        return this;
    }

    public PointLinksPage deletePointLink() {
        deletePointLink.click();
        return pointLinksPage;
    }

    public PointLinksPage savePointLink() {
        savePointLink.click();
        return pointLinksPage;
    }

    public String getSourcePointIdText() {
        return sourcePointId.getText();
    }

    public String getTargetPointIdText() {
        return targetPointId.getText();
    }

    public String getScript() {
        return script.getValue();
    }

    public EventType getEvent() {
        return EventType.getType(event.getText());
    }

    public boolean isDisabled() {
        return disabled.isEnabled();
    }

    @Override
    public PointLinksDetailsPage getPage() {
        return this;
    }
}
