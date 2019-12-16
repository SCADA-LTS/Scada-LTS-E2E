package org.scadalts.e2e.pages.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.pages.type.PageObject;

public abstract class PageObjectAbstract implements PageObject {

    @FindBy(tagName = "head")
    private SelenideElement head;

    @FindBy(tagName = "body")
    private SelenideElement body;

    @Override
    public String getHeadText() {
        return head.getText();
    }

    @Override
    public String getBodyText() {
        return body.getText();
    }

}
