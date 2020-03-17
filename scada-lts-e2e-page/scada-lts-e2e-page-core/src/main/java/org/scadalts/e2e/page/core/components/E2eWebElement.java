package org.scadalts.e2e.page.core.components;

import com.codeborne.selenide.SelenideElement;

public interface E2eWebElement {
    void click();
    void waitWhileVisible();
    void waitWhileNotVisible();

    E2eWebElement openInNewTab();

    static E2eWebElement newInstance(SelenideElement webElement) {
        return new WebElementClick(webElement);
    }
}
