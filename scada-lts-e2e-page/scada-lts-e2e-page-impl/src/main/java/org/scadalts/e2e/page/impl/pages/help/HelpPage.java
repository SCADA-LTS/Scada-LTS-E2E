package org.scadalts.e2e.page.impl.pages.help;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class HelpPage extends MainPageObjectAbstract<HelpPage> {

    public static final String TITLE = "Help";

    public HelpPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public HelpPage getPage() {
        return this;
    }
}