package org.scadalts.e2e.pages.page.alarms;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

public class PendingAlarmsPage extends MainPageObjectAbstract<PendingAlarmsPage> {

    public PendingAlarmsPage(SelenideElement source) {
        super(source);
    }

    @Override
    public PendingAlarmsPage getPage() {
        return this;
    }
}
