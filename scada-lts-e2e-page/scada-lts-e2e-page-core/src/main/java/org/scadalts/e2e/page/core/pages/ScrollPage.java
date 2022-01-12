package org.scadalts.e2e.page.core.pages;

import org.scadalts.e2e.page.core.javascripts.JavascriptExecutable;

public interface ScrollPage<T extends PageObject<T>> extends JavascriptExecutable, GetPage<T> {

    default T scrollDown() {
        return scrollDown(getScrollHight());
    }

    default T scrollDown(long pixel) {
        executeJs("window.scrollTo(0, {0});", String.valueOf(pixel));
        return getPage();
    }

    default T scrollUp() {
        executeJs("window.scrollTo(0, 0);");
        return getPage();
    }

    default long getScrollHight() {
        return executeJsLong(() -> "return document.body.scrollHeight;");
    }
}
