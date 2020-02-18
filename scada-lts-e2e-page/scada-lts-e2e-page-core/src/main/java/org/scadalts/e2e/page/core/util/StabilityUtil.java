package org.scadalts.e2e.page.core.util;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.refresh;
import static org.scadalts.e2e.common.utils.ExecutorUtil.executeConsumerThrowable;

@Log4j2
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

    public static SelenideElement refreshWhile(SelenideElement element, Condition condition) {
        long time = System.currentTimeMillis();
        int i = 0;
        while(element.is(condition)
                && System.currentTimeMillis() - time < 2 * Configuration.timeout
                && i < 1000000) {
            refresh();
            i++;
            logger.debug("i: {}", i);
            executeConsumerThrowable(Thread::sleep, Configuration.timeout/3, RuntimeException::new);
        }
        waitWhile(element, condition);
        return element;
    }

    public static SelenideElement refreshWaitWhile(SelenideElement element, Condition condition) {
        if(element.is(condition))
            refresh();
        waitWhile(element, condition);
        return element;
    }

}
