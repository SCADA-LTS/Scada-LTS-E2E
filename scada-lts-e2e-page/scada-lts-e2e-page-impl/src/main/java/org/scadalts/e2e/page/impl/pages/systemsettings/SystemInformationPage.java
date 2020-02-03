package org.scadalts.e2e.page.impl.pages.systemsettings;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class SystemInformationPage extends MainPageObjectAbstract<SystemInformationPage> {

    public static final String TITLE = "System information";

    public SystemInformationPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public SystemInformationPage getPage() {
        return this;
    }
}