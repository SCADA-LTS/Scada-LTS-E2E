package org.scadalts.e2e.page.impl.pages.reports;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class ReportsPage extends MainPageObjectAbstract<ReportsPage> {

    public static final String TITLE = "Report";

    public ReportsPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public ReportsPage getPage() {
        return this;
    }
}