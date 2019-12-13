package org.scadalts.e2e.pages.page.publishers;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

public class PublishersPage extends PageObjectAbstract<PublishersPage> {

    public PublishersPage(SelenideElement source) {
        super(source);
    }

    @Override
    public PublishersPage getPage() {
        return this;
    }
}