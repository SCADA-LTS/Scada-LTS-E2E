package org.scadalts.e2e.page.impl.pages.publishers;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class PublishersPage extends MainPageObjectAbstract<PublishersPage> {

    public static final String TITLE = "Publishers";

    public PublishersPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public PublishersPage getPage() {
        return this;
    }
}