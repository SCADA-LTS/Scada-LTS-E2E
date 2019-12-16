package org.scadalts.e2e.pages.page.mailinglists;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

public class MailingListsPage extends MainPageObjectAbstract<MailingListsPage> {

    public MailingListsPage(SelenideElement source) {
        super(source);
    }

    @Override
    public MailingListsPage getPage() {
        return this;
    }
}