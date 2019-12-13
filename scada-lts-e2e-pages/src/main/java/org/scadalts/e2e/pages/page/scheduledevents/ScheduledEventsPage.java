package org.scadalts.e2e.pages.page.scheduledevents;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

public class ScheduledEventsPage extends PageObjectAbstract<ScheduledEventsPage> {

    public ScheduledEventsPage(SelenideElement source) {
        super(source);
    }

    @Override
    public ScheduledEventsPage getPage() {
        return this;
    }
}