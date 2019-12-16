package org.scadalts.e2e.pages.page.compoundeventdetectors;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.MainPageObjectAbstract;

public class CompoundEventDetectorsPage extends MainPageObjectAbstract<CompoundEventDetectorsPage> {

    public CompoundEventDetectorsPage(SelenideElement source) {
        super(source);
    }

    @Override
    public CompoundEventDetectorsPage getPage() {
        return this;
    }
}