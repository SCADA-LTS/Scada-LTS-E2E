package org.scadalts.e2e.pages.page.publishers;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

public class PublishersPage extends MainPageObjectAbstract<PublishersPage> {

    public PublishersPage(SelenideElement source) {
        super(source);
    }

    @Override
    public PublishersPage getPage() {
        return this;
    }
}