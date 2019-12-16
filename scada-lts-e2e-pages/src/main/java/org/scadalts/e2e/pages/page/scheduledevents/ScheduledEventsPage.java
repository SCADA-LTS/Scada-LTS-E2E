package org.scadalts.e2e.pages.page.scheduledevents;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

public class ScheduledEventsPage extends MainPageObjectAbstract<ScheduledEventsPage> {

    public ScheduledEventsPage(SelenideElement source) {
        super(source);
    }

    @Override
    public ScheduledEventsPage getPage() {
        return this;
    }
}