package org.scadalts.e2e.page.impl.pages.scheduledevents;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class ScheduledEventsPage extends MainPageObjectAbstract<ScheduledEventsPage> {

    public static final String TITLE = "Scheduled events";

    public ScheduledEventsPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public ScheduledEventsPage getPage() {
        return this;
    }
}