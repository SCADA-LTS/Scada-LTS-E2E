package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.components.E2eWebElement;

public abstract class MainPageObjectAbstract<T extends MainPageObject<T>> extends PageObjectAbstract<T>
        implements MainPageObject<T> {

    private final SelenideElement source;

    public MainPageObjectAbstract(SelenideElement source, String title) {
        super(title);
        this.source = source;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }

}
