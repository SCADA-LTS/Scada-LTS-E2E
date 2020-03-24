package org.scadalts.e2e.page.core.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.utils.StabilityUtil;
import org.scadalts.e2e.page.core.config.PageConfiguration;
import org.scadalts.e2e.page.core.pages.MainPageObject;

import java.util.function.Predicate;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.refresh;
import static org.scadalts.e2e.common.utils.StabilityUtil.Timeout;
import static org.scadalts.e2e.common.utils.StabilityUtil.executeWhile;

@Log4j2
public abstract class PageStabilityUtil {

    private static Timeout timeout = new Timeout(3 * PageConfiguration.timeout);

    public static SelenideElement waitWhileNotVisible(SelenideElement webElement) {
        return waitWhile(webElement, not(Condition.visible));
    }

    public static SelenideElement waitWhile(SelenideElement element, Condition condition) {
        return element.waitWhile(condition, PageConfiguration.timeout);
    }

    public static <T> void waitWhile(Predicate<T> condition, T arg) {
        StabilityUtil.waitWhile(condition, arg, timeout);
    }

    public static <T> void waitWhile(Predicate<T> condition, T arg, Timeout timeout) {
        StabilityUtil.waitWhile(condition, arg, timeout);
    }

    public static <T> T refreshWhile(Predicate<T> condition, T arg) {
        return executeWhile(condition, arg, Selenide::refresh, timeout);
    }

    public static SelenideElement refreshWhile(SelenideElement element, Condition condition) {
        refreshWhile(element::is,condition);
        waitWhile(element, condition);
        return element;
    }

    public static <T extends MainPageObject<T>> SelenideElement reopenWaitWhile(MainPageObject<T> page,
                                                                                SelenideElement element,
                                                                                Condition condition) {
        executeWhile(element::is, condition, page::reopen, timeout);
        waitWhile(element, condition);
        return element;
    }

    public static <T extends MainPageObject<T>> SelenideElement reopenWhile(MainPageObject<T> page,
                                                                            SelenideElement element,
                                                                            Condition condition) {
        executeWhile(element::is, condition, page::reopen, timeout);
        return element;
    }

    public static <T extends MainPageObject<T>> SelenideElement openPageWhile(MainPageObject<T> page,
                                                                              SelenideElement element,
                                                                              Condition condition) {
        executeWhile(element::is, condition, page::openPage, timeout);
        return element;
    }

    public static <T extends MainPageObject<T>> SelenideElement openPageWaitWhile(MainPageObject<T> page,
                                                                                  SelenideElement element,
                                                                                  Condition condition) {
        executeWhile(element::is, condition, page::openPage, timeout);
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
