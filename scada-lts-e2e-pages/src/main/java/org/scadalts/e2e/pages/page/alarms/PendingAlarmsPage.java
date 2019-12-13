package org.scadalts.e2e.pages.page.alarms;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

public class PendingAlarmsPage extends PageObjectAbstract<PendingAlarmsPage> {

    public PendingAlarmsPage(SelenideElement source) {
        super(source);
    }

    @Override
    public PendingAlarmsPage getPage() {
        return this;
    }
}
