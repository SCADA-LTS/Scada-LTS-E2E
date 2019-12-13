package org.scadalts.e2e.pages.component;

import org.openqa.selenium.WebElement;

public interface WebElementClickable {
    void click();

    WebElement openInNewTab();

    static WebElementClickable newInstance(WebElement webElement) {
        return new WebElementClick(webElement);
    }
}
