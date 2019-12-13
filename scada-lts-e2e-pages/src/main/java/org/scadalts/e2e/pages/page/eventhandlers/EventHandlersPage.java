package org.scadalts.e2e.pages.page.eventhandlers;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

public class EventHandlersPage extends PageObjectAbstract<EventHandlersPage> {

    public EventHandlersPage(SelenideElement source) {
        super(source);
    }

    @Override
    public EventHandlersPage getPage() {
        return this;
    }
}