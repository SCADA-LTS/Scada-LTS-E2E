package org.scadalts.e2e.page.core.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.scadalts.e2e.page.core.config.PageConfiguration;

import java.util.Set;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.switchTo;

import static org.scadalts.e2e.page.core.utils.E2eWebDriverProvider.getDriver;
import static org.scadalts.e2e.page.core.utils.PageStabilityUtil.waitWhile;

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
    public void waitWhileVisible() {
        waitWhile(webElement, Condition.visible);
    }

    @Override
    public void waitWhileNotVisible() {
        waitWhile(webElement, not(Condition.visible));
    }

    @Override
    public WebElementClick openInNewTab() {

        String newTab = Keys.chord(PageConfiguration.ctrl, Keys.RETURN);
        webElement.sendKeys(newTab);
        Set<String> tabs = getDriver().getWindowHandles();
        switchTo().window(tabs.size() - 1);
        return this;
    }
}
