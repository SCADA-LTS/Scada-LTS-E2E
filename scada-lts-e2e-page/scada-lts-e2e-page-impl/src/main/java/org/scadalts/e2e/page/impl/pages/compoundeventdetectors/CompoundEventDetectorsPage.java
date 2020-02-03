package org.scadalts.e2e.page.impl.pages.compoundeventdetectors;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class CompoundEventDetectorsPage extends MainPageObjectAbstract<CompoundEventDetectorsPage> {

    public static final String TITLE = "Compound event detectors";

    public CompoundEventDetectorsPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public CompoundEventDetectorsPage getPage() {
        return this;
    }
}