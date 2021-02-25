package org.scadalts.e2e.page.impl.pages.pointhierarchy;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class PointHierarchyPage extends MainPageObjectAbstract<PointHierarchyPage> {

    @FindBy(css = "a[href='pointHierarchySLTS']")
    private SelenideElement source;

    public static final String TITLE = "Point Hierarchy";

    public PointHierarchyPage() {
        super(TITLE, "/pointHierarchySLTS");
    }

    @Override
    public PointHierarchyPage getPage() {
        return this;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }

}