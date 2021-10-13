package org.scadalts.e2e.page.core.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NotFoundException;
import org.scadalts.e2e.common.core.utils.StabilityUtil;
import org.scadalts.e2e.page.core.config.PageConfiguration;
import org.scadalts.e2e.page.core.pages.MainPageObject;
import org.scadalts.e2e.page.core.pages.PageObject;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.refresh;
import static org.scadalts.e2e.common.core.utils.StabilityUtil.Timeout;

@Log4j2
public abstract class PageStabilityUtil {

    private static Timeout timeout = new Timeout(3 * PageConfiguration.timeout);


    public static SelenideElement waitWhileNotVisible(SelenideElement webElement) {
        try {
            return waitWhile(webElement, not(Condition.visible));
        } catch (Exception ex) {
            throw new NotFoundException(ex);
        }
    }

    public static SelenideElement waitWhile(SelenideElement element, Condition condition) {
        try {
            return element.waitWhile(condition, PageConfiguration.timeout);
        } catch (Exception ex) {
            throw new NotFoundException(ex);
        }
    }

    public static <T> void waitWhile(Predicate<T> condition, T arg) {
        waitWhile(condition, arg, true);
    }

    public static <T> T waitWhile(Predicate<T> condition, T arg, boolean safe) {
        try {
            return StabilityUtil.waitWhile(condition, arg, timeout, safe);
        } catch (Exception ex) {
            throw new NotFoundException(ex);
        }
    }

    public static void waitWhile(BooleanSupplier condition) {
        waitWhile(condition, true);
    }

    public static void waitWhile(BooleanSupplier condition, boolean safe) {
        try {
            StabilityUtil.waitWhile(condition, timeout, safe);
        } catch (Exception ex) {
            throw new NotFoundException(ex);
        }
    }

    public static <T> void waitWhile(Predicate<T> condition, T arg, Timeout timeout) {
        waitWhile(condition, arg, timeout, true);
    }

    public static <T> void waitWhile(Predicate<T> condition, T arg, Timeout timeout, boolean safe) {
        try {
            StabilityUtil.waitWhile(condition, arg, timeout, safe);
        } catch (Exception ex) {
            throw new NotFoundException(ex);
        }
    }

    public static <T> T refreshWhile(Predicate<T> condition, T arg) {
        return refreshWhile(condition, arg, true);
    }

    public static <T> T refreshWhile(Predicate<T> condition, T arg, boolean safe) {
        try {
            return StabilityUtil.executeWhile(condition, arg, Selenide::refresh, timeout, safe);
        } catch (Exception ex) {
            throw new NotFoundException(ex);
        }
    }

    public static SelenideElement refreshWhile(SelenideElement element, Condition condition) {
        return refreshWhile(element, condition, true);
    }

    public static SelenideElement refreshWhile(SelenideElement element, Condition condition, boolean safe) {
        refreshWhile(element::is, condition, safe);
        waitWhile(element, condition);
        return element;
    }

    public static <T extends MainPageObject<T>> SelenideElement reopenWaitWhile(MainPageObject<T> page,
                                                                                SelenideElement element,
                                                                                Condition condition) {
        return reopenWaitWhile(page, element, condition, true);
    }

    public static <T extends MainPageObject<T>> SelenideElement reopenWaitWhile(MainPageObject<T> page,
                                                                                SelenideElement element,
                                                                                Condition condition,
                                                                                boolean safe) {
        try {
            StabilityUtil.executeWhile(element::is, condition, page::reopen, timeout, safe);
            waitWhile(element, condition);
            return element;
        } catch (Exception ex) {
            page.screenshot();
            throw new NotFoundException(ex);
        }
    }

    public static <T extends MainPageObject<T>> SelenideElement reopenWhile(MainPageObject<T> page,
                                                                            SelenideElement element,
                                                                            Condition condition) {
        return reopenWhile(page, element, condition, true);
    }

    public static <T extends MainPageObject<T>> SelenideElement reopenWhile(MainPageObject<T> page,
                                                                            SelenideElement element,
                                                                            Condition condition,
                                                                            boolean safe) {
        try {
            StabilityUtil.executeWhile(element::is, condition, page::reopen, timeout, safe);
            return element;
        } catch (Exception ex) {
            page.screenshot();
            throw new NotFoundException(ex);
        }
    }

    public static <T extends MainPageObject<T>> SelenideElement reopenWhile(MainPageObject<T> page, SelenideElement element,
                                                                            Predicate<SelenideElement> predicate) {
        return reopenWhile(page, element, predicate, true);
    }

    public static <T extends MainPageObject<T>> SelenideElement reopenWhile(MainPageObject<T> page, SelenideElement element,
                                                                            Predicate<SelenideElement> predicate,
                                                                            boolean safe) {
        try {
            StabilityUtil.executeWhile(predicate, element, page::reopen, timeout, safe);
            return element;
        } catch (Exception ex) {
            page.screenshot();
            throw new NotFoundException(ex);
        }

    }

    public static <T extends MainPageObject<T>> SelenideElement openPageWhile(MainPageObject<T> page,
                                                                              SelenideElement element,
                                                                              Condition condition) {
        return openPageWaitWhile(page, element, condition, true);
    }

    public static <T extends MainPageObject<T>> SelenideElement openPageWhile(MainPageObject<T> page,
                                                                              SelenideElement element,
                                                                              Condition condition,
                                                                              boolean safe) {
        try {
            StabilityUtil.executeWhile(element::is, condition, page::openPage, timeout, safe);
            return element;
        } catch (Exception ex) {
            page.screenshot();
            throw new NotFoundException(ex);
        }
    }

    public static <T extends MainPageObject<T>> SelenideElement openPageWaitWhile(MainPageObject<T> page,
                                                                                  SelenideElement element,
                                                                                  Condition condition) {
        return openPageWaitWhile(page, element, condition, true);
    }

    public static <T extends MainPageObject<T>> SelenideElement openPageWaitWhile(MainPageObject<T> page,
                                                                                  SelenideElement element,
                                                                                  Condition condition,
                                                                                  boolean safe) {
        try {
            StabilityUtil.executeWhile(element::is, condition, page::openPage, timeout, safe);
            waitWhile(element, condition);
            return element;
        } catch (Exception ex) {
            page.screenshot();
            throw new NotFoundException(ex);
        }
    }

    public static <T extends PageObject<T>, R> void executeWhile(PageObject<T> page,
                                                                     Supplier<R> executor,
                                                                     Predicate<R> condition) {
        try {
            StabilityUtil.executeWhile(condition, executor, timeout, true);
        } catch (Exception ex) {
            page.screenshot();
            throw new NotFoundException(ex);
        }
    }

    public static SelenideElement refreshWaitWhile(SelenideElement element, Condition condition) {
        if(element.is(condition))
            refresh();
        waitWhile(element, condition);
        return element;
    }

}
