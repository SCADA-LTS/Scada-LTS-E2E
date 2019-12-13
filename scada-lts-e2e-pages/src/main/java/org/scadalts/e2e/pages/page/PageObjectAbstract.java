package org.scadalts.e2e.pages.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.pages.component.WebElementClickable;

public abstract class PageObjectAbstract<T> implements PageObject<T> {

    @FindBy(tagName = "head")
    private SelenideElement head;

    @FindBy(tagName = "body")
    private SelenideElement body;

    private final SelenideElement source;

    public PageObjectAbstract(SelenideElement source) {
        this.source = source;
    }

    @Override
    public String getHeadText() {
        return head.getText();
    }

    @Override
    public String getBodyText() {
        return body.getText();
    }

    @Override
    public WebElementClickable getSource() {
        return WebElementClickable.newInstance(source);
    }

}
