package org.scadalts.e2e.page.impl.pages.help;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class HelpPage extends MainPageObjectAbstract<HelpPage> {

    @FindBy(css = "a[href='help.shtm']")
    private SelenideElement source;

    public static final String TITLE = "Help";

    public HelpPage() {
        super(TITLE, "/help.shtm");
    }

    @Override
    public HelpPage getPage() {
        return this;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }
}