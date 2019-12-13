package org.scadalts.e2e.pages.page.pointlinks;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

public class PointLinksPage extends PageObjectAbstract<PointLinksPage> {

    public PointLinksPage(SelenideElement source) {
        super(source);
    }

    @Override
    public PointLinksPage getPage() {
        return this;
    }
}