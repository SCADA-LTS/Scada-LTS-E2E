package org.scadalts.e2e.pages.component;

import org.openqa.selenium.WebElement;
import org.scadalts.e2e.pages.config.E2eConfigurator;

import java.util.Set;

import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

class WebElementClick implements WebElementClickable {

    private WebElement webElement;

    WebElementClick(WebElement webElement) {
        this.webElement = webElement;
    }

    @Override
    public void click() {
        webElement.click();
    }

    @Override
    public WebElement openInNewTab() {
        webElement.sendKeys(E2eConfigurator.NEW_TAB_SHORTCUTS);
        Set<String> tabs = getWebDriver().getWindowHandles();
        switchTo().window(tabs.size() - 1);
        return webElement;
    }
}
