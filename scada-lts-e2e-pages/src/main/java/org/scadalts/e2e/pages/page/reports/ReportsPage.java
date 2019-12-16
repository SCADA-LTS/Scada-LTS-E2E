package org.scadalts.e2e.pages.page.reports;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

public class ReportsPage extends MainPageObjectAbstract<ReportsPage> {

    public ReportsPage(SelenideElement source) {
        super(source);
    }

    @Override
    public ReportsPage getPage() {
        return this;
    }
}