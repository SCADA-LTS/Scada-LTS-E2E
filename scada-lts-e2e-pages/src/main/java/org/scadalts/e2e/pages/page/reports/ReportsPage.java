package org.scadalts.e2e.pages.page.reports;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

public class ReportsPage extends PageObjectAbstract<ReportsPage> {

    public ReportsPage(SelenideElement source) {
        super(source);
    }

    @Override
    public ReportsPage getPage() {
        return this;
    }
}