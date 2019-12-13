package org.scadalts.e2e.pages.page.maintenanceevents;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

public class MaintenanceEventsPage extends PageObjectAbstract<MaintenanceEventsPage> {

    public MaintenanceEventsPage(SelenideElement source) {
        super(source);
    }

    @Override
    public MaintenanceEventsPage getPage() {
        return this;
    }
}