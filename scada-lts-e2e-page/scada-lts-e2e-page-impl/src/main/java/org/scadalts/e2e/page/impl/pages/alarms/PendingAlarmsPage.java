package org.scadalts.e2e.page.impl.pages.alarms;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class PendingAlarmsPage extends MainPageObjectAbstract<PendingAlarmsPage> {

    public static final String TITLE = "Pending alarms";

    public PendingAlarmsPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public PendingAlarmsPage getPage() {
        return this;
    }

}
