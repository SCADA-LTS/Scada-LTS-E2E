package org.scadalts.e2e.page.core.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.refresh;
import static org.scadalts.e2e.common.utils.StabilityUtil.*;

@Log4j2
public abstract class PageStabilityUtil {

    public static SelenideElement waitWhileNotVisible(SelenideElement webElement) {
        return waitWhile(webElement, not(Condition.visible));
    }

    public static SelenideElement waitWhile(SelenideElement element, Condition condition) {
        return element.waitWhile(condition, Configuration.timeout);
    }

    public static SelenideElement refreshWhile(SelenideElement element, Condition condition) {
        long time = System.currentTimeMillis();
        Timeout timeout = new Timeout(3 * Configuration.timeout);
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
