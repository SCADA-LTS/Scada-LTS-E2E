package org.scadalts.e2e.page.impl.pages.reports;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class ReportsPage extends MainPageObjectAbstract<ReportsPage> {

    @FindBy(css = "a[href='reports.shtm']")
    private SelenideElement source;

    public static final String TITLE = "Report";

    public ReportsPage() {
        super(TITLE, "/reports.shtm");
    }

    @Override
    public ReportsPage getPage() {
        return this;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }
}