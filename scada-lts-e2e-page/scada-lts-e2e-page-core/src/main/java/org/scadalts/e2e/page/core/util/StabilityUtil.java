package org.scadalts.e2e.page.core.util;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Selenide.$;

public abstract class StabilityUtil {

    public static SelenideElement reloadElement(SelenideElement webElement) {
        WebDriver webDriver = WebDriverRunner.getAndCheckWebDriver();
        WebElement reloaded = new WebDriverWait(webDriver, Configuration.timeout)
                .until(ExpectedConditions.visibilityOf(webElement));
        return $(reloaded);
    }

    public static SelenideElement waitWhile(SelenideElement element, Condition text) {
        return element.waitWhile(text, Configuration.timeout);
    }
}
