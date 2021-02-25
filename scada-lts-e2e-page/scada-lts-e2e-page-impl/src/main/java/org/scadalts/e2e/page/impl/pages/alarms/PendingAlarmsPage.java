package org.scadalts.e2e.page.impl.pages.alarms;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class PendingAlarmsPage extends MainPageObjectAbstract<PendingAlarmsPage> {

    @FindBy(css = "a[href='events.shtm']")
    private SelenideElement source;

    public static final String TITLE = "Pending alarms";

    public PendingAlarmsPage() {
        super(TITLE, "/events.shtm");
    }

    @Override
    public PendingAlarmsPage getPage() {
        return this;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }

}
