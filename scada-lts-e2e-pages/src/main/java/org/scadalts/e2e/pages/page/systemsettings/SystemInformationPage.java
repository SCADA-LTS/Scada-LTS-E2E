package org.scadalts.e2e.pages.page.systemsettings;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

public class SystemInformationPage extends MainPageObjectAbstract<SystemInformationPage> {

    public SystemInformationPage(SelenideElement source) {
        super(source);
    }

    @Override
    public SystemInformationPage getPage() {
        return this;
    }
}