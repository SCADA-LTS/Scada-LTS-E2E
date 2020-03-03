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
import static org.scadalts.e2e.page.core.utils.E2eUtil.acceptAlert;
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

    public final static String TITLE = "Event handler";

    public EditEventHandlersPage() {
        super(TITLE);
    }

    @Override
    public EditEventHandlersPage getPage() {
        return this;
    }


    public EditEventHandlersPage setAlisas(EventHandlerIdentifier scriptName) {
        waitWhile(alias, not(Condition.visible)).setValue(scriptName.getValue());
        return this;
    }

    public EditEventHandlersPage setXid(Xid eventHandlerXid) {
        waitWhile(xid, not(Condition.visible)).setValue(eventHandlerXid.getValue());
        return this;
    }

    public EditEventHandlersPage setDisabled(boolean disabled) {
        if(disabled)
            waitWhile(disabledCheckbox, not(Condition.visible)).click();
        return this;
    }

    public EditEventHandlersPage setEventHandlerType(EventHandlerType eventHandlerType) {
        waitWhile(handlerTypeSelect, not(Condition.visible)).selectOption(eventHandlerType.getName());
        return this;
    }

    public EditEventHandlersPage setActiveScriptCommand(ScriptCriteria criteria) {
        $(By.id("activeScriptCommand")).selectOption(criteria.getIdentifier().getValue());
        return this;
    }

    public EditEventHandlersPage setInactiveScriptCommand(ScriptCriteria criteria) {
        $(By.id("inactiveScriptCommand")).selectOption(criteria.getIdentifier().getValue());
        return this;
    }

    public EventHandlerType getEventHandlerType() {
        String value = waitWhile(handlerTypeSelect, not(Condition.visible)).getSelectedText();
        return EventHandlerType.getType(value);
    }

    public ScriptIdentifier getActiveScriptCommand() {
        String value = $(By.id("activeScriptCommand")).getSelectedText();
        return new ScriptIdentifier(value);
    }

    public ScriptIdentifier getInactiveScriptCommand() {
        String value = $(By.id("inactiveScriptCommand")).getSelectedText();
        return new ScriptIdentifier(value);
    }

    public EventHandlerIdentifier getAlias() {
        String value = alias.getValue();
        return new EventHandlerIdentifier(value);
    }

    public Xid getXid() {
        String value = xid.getValue();
        return new Xid(value);
    }

    public boolean isDisabled() {
        String value = disabledCheckbox.getValue();
        return Boolean.valueOf(value);
    }

    public EditEventHandlersPage saveEventHandler() {
        waitWhile(saveHandler, not(Condition.visible)).click();
        acceptAlert();
        acceptAlert();
        return this;
    }
}
