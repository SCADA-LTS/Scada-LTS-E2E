package org.scadalts.e2e.page.impl.pages.mailinglists;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class MailingListsPage extends MainPageObjectAbstract<MailingListsPage> {

    public static final String TITLE = "Mailing lists";

    public MailingListsPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public MailingListsPage getPage() {
        return this;
    }
}