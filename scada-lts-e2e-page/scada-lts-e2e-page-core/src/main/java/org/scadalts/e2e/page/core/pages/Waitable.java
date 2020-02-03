package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.util.StabilityUtil;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;

interface Waitable<T extends PageObject<T>> extends GetPage<T> {

    default T waitOnPage(long wait) {
        Selenide.sleep(wait);
        return getPage();
    }

    default T waitForObject(String css) {
        StabilityUtil.waitWhile($(By.cssSelector(css)), not(Condition.visible));
        return getPage();
    }
}
