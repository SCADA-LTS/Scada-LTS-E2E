package org.scadalts.e2e.pages.page.help;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

public class HelpPage extends MainPageObjectAbstract<HelpPage> {

    public HelpPage(SelenideElement source) {
        super(source);
    }

    @Override
    public HelpPage getPage() {
        return this;
    }
}