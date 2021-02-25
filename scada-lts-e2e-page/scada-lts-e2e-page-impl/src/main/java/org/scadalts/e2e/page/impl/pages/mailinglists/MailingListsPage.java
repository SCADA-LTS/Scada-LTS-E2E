package org.scadalts.e2e.page.impl.pages.mailinglists;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class MailingListsPage extends MainPageObjectAbstract<MailingListsPage> {

    @FindBy(css = "a[href='mailing_lists.shtm']")
    private SelenideElement source;

    public static final String TITLE = "Mailing lists";

    public MailingListsPage() {
        super(TITLE, "/mailing_lists.shtm");
    }

    @Override
    public MailingListsPage getPage() {
        return this;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }
}