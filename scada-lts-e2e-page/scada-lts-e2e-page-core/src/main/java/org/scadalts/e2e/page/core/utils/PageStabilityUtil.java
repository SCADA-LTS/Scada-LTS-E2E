package org.scadalts.e2e.page.core.utils;

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
import static org.scadalts.e2e.common.utils.StabilityUtil.*;

@Log4j2
public abstract class PageStabilityUtil {

    public static SelenideElement reloadElement(SelenideElement webElement) {
        WebDriver webDriver = WebDriverRunner.getAndCheckWebDriver();
        WebElement reloaded = new WebDriverWait(webDriver, Configuration.timeout)
                .until(ExpectedConditions.visibilityOf(webElement));
        return $(reloaded);
    }

    public static SelenideElement waitWhile(SelenideElement element, Condition condition) {
        return element.waitWhile(condition, Configuration.timeout);
    }

    public static SelenideElement refreshWhile(SelenideElement element, Condition condition) {
        long time = System.currentTimeMillis();
        Timeout timeout = new Timeout(2 * Configuration.timeout);
        int i = 0;
        refresh();
        while(element.is(condition)
                && !isExceededTimeout(timeout, time)
                && !isExceededLimit(i)) {
            i++;
            logger.debug("i: {}", i);
            sleep();
            refresh();
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
