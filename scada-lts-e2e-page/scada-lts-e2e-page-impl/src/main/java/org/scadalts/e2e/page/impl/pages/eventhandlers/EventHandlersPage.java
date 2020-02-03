package org.scadalts.e2e.page.impl.pages.eventhandlers;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;


public class EventHandlersPage extends MainPageObjectAbstract<EventHandlersPage> {

    public static final String TITLE = "Event handlers";

    public EventHandlersPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public EventHandlersPage getPage() {
        return this;
    }
}