package org.scadalts.e2e.page.impl.pages.eventhandlers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.ScriptCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventHandlerIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.ScriptIdentifier;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static javax.xml.bind.DatatypeConverter.parseBoolean;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAlertAfterClick;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

public class EditEventHandlersPage extends PageObjectAbstract<EditEventHandlersPage> {

    @FindBy(id = "handlerTypeSelect")
    private SelenideElement handlerTypeSelect;

    @FindBy(id = "xid")
    private SelenideElement xid;

    @FindBy(id = "alias")
    private SelenideElement alias;

    @FindBy(id = "disabled")
    private SelenideElement disabledCheckbox;

    @FindBy(css = "img[onclick*='saveHandler']")
    private SelenideElement saveHandler;

    @FindBy(id = "deleteImg")
    private SelenideElement delete;

    private EventHandlersPage eventHandlersPage;

    public final static String TITLE = "Event handler";

    public EditEventHandlersPage(EventHandlersPage eventHandlersPage) {
        super(TITLE);
        this.eventHandlersPage = eventHandlersPage;
    }

    @Override
    public EditEventHandlersPage getPage() {
        return this;
    }


    public EditEventHandlersPage setAlisas(EventHandlerIdentifier scriptName) {
        delay();
        waitWhile(alias, not(Condition.visible)).setValue(scriptName.getValue());
        return this;
    }

    public EditEventHandlersPage setXid(Xid eventHandlerXid) {
        delay();
        waitWhile(xid, not(Condition.visible)).setValue(eventHandlerXid.getValue());
        return this;
    }

    public EditEventHandlersPage setDisabled(boolean disabled) {
        delay();
        if(disabled)
            waitWhile(disabledCheckbox, not(Condition.visible)).click();
        return this;
    }

    public EditEventHandlersPage setEventHandlerType(EventHandlerType eventHandlerType) {
        delay();
        waitWhile(handlerTypeSelect, not(Condition.visible)).selectOption(eventHandlerType.getName());
        return this;
    }

    public EditEventHandlersPage setActiveScriptCommand(ScriptCriteria criteria) {
        delay();
        $(By.id("activeScriptCommand")).selectOption(criteria.getIdentifier().getValue());
        return this;
    }

    public EditEventHandlersPage setInactiveScriptCommand(ScriptCriteria criteria) {
        delay();
        $(By.id("inactiveScriptCommand")).selectOption(criteria.getIdentifier().getValue());
        return this;
    }

    public EventHandlerType getEventHandlerType() {
        delay();
        String value = waitWhile(handlerTypeSelect, not(Condition.visible)).getSelectedText();
        return EventHandlerType.getType(value);
    }

    public ScriptIdentifier getActiveScriptCommand() {
        String value = $(By.id("activeScriptCommand")).getSelectedText();
        return new ScriptIdentifier(value);
    }

    public ScriptIdentifier getInactiveScriptCommand() {
        delay();
        String value = $(By.id("inactiveScriptCommand")).getSelectedText();
        return new ScriptIdentifier(value);
    }

    public EventHandlerIdentifier getAlias() {
        delay();
        String value = alias.getValue();
        EventHandlerType type = getEventHandlerType();
        return new EventHandlerIdentifier(value, type);
    }

    public Xid getXid() {
        delay();
        String value = xid.getValue();
        return new Xid(value);
    }

    public boolean isDisabled() {
        delay();
        return parseBoolean(disabledCheckbox.getAttribute("selected"));
    }

    public EditEventHandlersPage saveEventHandler() {
        delay();
        SelenideElement selenideElement = waitWhile(saveHandler, not(Condition.visible));
        acceptAlertAfterClick(selenideElement);
        return this;
    }

    public EventHandlersPage deleteEventHandler() {
        delay();
        waitWhile(saveHandler, not(Condition.visible)).click();
        return eventHandlersPage;
    }
}
