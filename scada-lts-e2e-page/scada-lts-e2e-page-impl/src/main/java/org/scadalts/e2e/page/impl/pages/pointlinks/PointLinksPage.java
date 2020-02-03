package org.scadalts.e2e.page.impl.pages.pointlinks;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class PointLinksPage extends MainPageObjectAbstract<PointLinksPage> {

    public static final String TITLE = "Point links";

    public PointLinksPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public PointLinksPage getPage() {
        return this;
    }
}