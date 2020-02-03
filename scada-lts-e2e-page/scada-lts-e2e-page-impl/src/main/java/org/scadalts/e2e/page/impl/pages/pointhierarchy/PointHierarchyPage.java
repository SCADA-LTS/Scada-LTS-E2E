package org.scadalts.e2e.page.impl.pages.pointhierarchy;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class PointHierarchyPage extends MainPageObjectAbstract<PointHierarchyPage> {

    public static final String TITLE = "Point Hierarchy";

    public PointHierarchyPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public PointHierarchyPage getPage() {
        return this;
    }

}