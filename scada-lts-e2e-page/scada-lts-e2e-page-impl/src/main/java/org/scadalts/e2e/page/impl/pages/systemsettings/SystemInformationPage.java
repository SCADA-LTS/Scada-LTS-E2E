package org.scadalts.e2e.page.impl.pages.systemsettings;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class SystemInformationPage extends MainPageObjectAbstract<SystemInformationPage> {

    @FindBy(css = "a[href='system_settings.shtm']")
    private SelenideElement source;

    public static final String TITLE = "System information";

    public SystemInformationPage() {
        super(TITLE, "/system_settings.shtm");
    }

    @Override
    public SystemInformationPage getPage() {
        return this;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }
}