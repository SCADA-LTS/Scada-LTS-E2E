package org.scadalts.e2e.pages.page.maintenanceevents;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

public class MaintenanceEventsPage extends MainPageObjectAbstract<MaintenanceEventsPage> {

    public MaintenanceEventsPage(SelenideElement source) {
        super(source);
    }

    @Override
    public MaintenanceEventsPage getPage() {
        return this;
    }
}