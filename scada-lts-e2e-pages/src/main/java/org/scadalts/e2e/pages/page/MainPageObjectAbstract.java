package org.scadalts.e2e.pages.page;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.type.MainPageObject;
import org.scadalts.e2e.pages.component.WebElementClickable;

public abstract class MainPageObjectAbstract<T> extends PageObjectAbstract
        implements MainPageObject<T> {

    private final SelenideElement source;

    public MainPageObjectAbstract(SelenideElement source) {
        this.source = source;
    }

    @Override
    public WebElementClickable getSource() {
        return WebElementClickable.newInstance(source);
    }

}
