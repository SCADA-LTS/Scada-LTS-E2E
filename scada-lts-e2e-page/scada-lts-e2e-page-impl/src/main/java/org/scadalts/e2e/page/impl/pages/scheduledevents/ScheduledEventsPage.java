package org.scadalts.e2e.page.impl.pages.scheduledevents;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class ScheduledEventsPage extends MainPageObjectAbstract<ScheduledEventsPage> {

    @FindBy(css = "a[href='scheduled_events.shtm']")
    private SelenideElement source;

    public static final String TITLE = "Scheduled events";

    public ScheduledEventsPage() {
        super(TITLE, "/scheduled_events.shtm");
    }

    @Override
    public ScheduledEventsPage getPage() {
        return this;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }
}