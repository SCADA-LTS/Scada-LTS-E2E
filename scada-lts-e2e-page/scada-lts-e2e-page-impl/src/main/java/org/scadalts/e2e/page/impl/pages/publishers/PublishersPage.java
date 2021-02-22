package org.scadalts.e2e.page.impl.pages.publishers;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class PublishersPage extends MainPageObjectAbstract<PublishersPage> {

    @FindBy(css = "a[href='publishers.shtm']")
    private SelenideElement source;

    public static final String TITLE = "Publishers";

    public PublishersPage() {
        super(TITLE, "/publishers.shtm");
    }

    @Override
    public PublishersPage getPage() {
        return this;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }
}