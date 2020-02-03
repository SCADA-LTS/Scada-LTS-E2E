package org.scadalts.e2e.page.impl.pages.maintenanceevents;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class MaintenanceEventsPage extends MainPageObjectAbstract<MaintenanceEventsPage> {

    public static final String TITLE = "Maintenance events";

    public MaintenanceEventsPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public MaintenanceEventsPage getPage() {
        return this;
    }
}