package org.scadalts.e2e.pages.page.eventhandlers;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

public class EventHandlersPage extends MainPageObjectAbstract<EventHandlersPage> {

    public EventHandlersPage(SelenideElement source) {
        super(source);
    }

    @Override
    public EventHandlersPage getPage() {
        return this;
    }
}