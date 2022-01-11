package org.scadalts.e2e.page.core.pages;

import com.codeborne.selenide.Selenide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public interface GetScreenshot<T extends PageObject<T>> extends GetPage<T> {

    Logger logger = LogManager.getLogger(GetScreenshot.class);

    default T screenshotWithoutScroll(String fileName) {
        try {
            Selenide.sleep(100);
            Selenide.screenshot(fileName);
        } catch (Throwable throwable) {
            logger.warn(throwable.getMessage(), throwable);
        }
        return getPage();
    }

    default T screenshot() {
        return screenshot(("screenshot_" + LocalDateTime.now()).replace(":", "_"));
    }

    default T screenshot(String fileName) {
        T page = getPage();
        try {
            long high = page.getScrollHight();
            long sum = 0;
            int limit = 100;
            int step = page.getWindowHeight() - 150;
            if(high < step)
                return page.screenshotWithoutScroll(fileName + "_" + high);
            while(sum <= high && limit > 0) {
                page.scrollDown(sum)
                        .screenshotWithoutScroll(fileName + "_" + sum);
                sum+=step;
                limit--;
            }
            return page.scrollDown().screenshotWithoutScroll(fileName + "_" + high);
        } catch (Throwable throwable) {
            logger.warn(throwable.getMessage(), throwable);
        }
        return page;
    }
}
