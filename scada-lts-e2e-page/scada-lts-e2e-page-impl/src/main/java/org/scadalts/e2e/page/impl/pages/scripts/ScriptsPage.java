package org.scadalts.e2e.page.impl.pages.scripts;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class ScriptsPage extends MainPageObjectAbstract<ScriptsPage> {

    public static final String TITLE = "Scripts";

    public ScriptsPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public ScriptsPage getPage() {
        return this;
    }
}
