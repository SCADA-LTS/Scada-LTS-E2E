package org.scadalts.e2e.page.core.components;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.scadalts.e2e.page.core.config.PageConfiguration;

import java.util.Set;

import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

class WebElementClick implements E2eWebElement {

    private SelenideElement webElement;

    WebElementClick(SelenideElement webElement) {
        this.webElement = webElement;
    }

    @Override
    public void click() {
        webElement.click();
    }

    @Override
    public WebElementClick openInNewTab() {
        String newTab = Keys.chord(String.valueOf((char)PageConfiguration.ctrl), Keys.RETURN);
        webElement.sendKeys(newTab);
        Set<String> tabs = getWebDriver().getWindowHandles();
        switchTo().window(tabs.size() - 1);
        return this;
    }
}
