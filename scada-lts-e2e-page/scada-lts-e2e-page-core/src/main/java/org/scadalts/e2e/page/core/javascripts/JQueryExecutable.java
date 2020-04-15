package org.scadalts.e2e.page.core.javascripts;

import org.scadalts.e2e.page.core.utils.PageStabilityUtil;

public interface JQueryExecutable extends JavascriptExecutable {

    default Object executeJQuery(String jQueryScript) {
        waitLoadedJQuery();
        return executeJs(jQueryScript);
    }

    default String executeJQueryString(String jQueryScript) {
        return String.valueOf(executeJQuery(jQueryScript));
    }

    default void waitLoadedJQuery() {
        PageStabilityUtil.waitWhile(() -> !executeJsBoolean(()->"return !!window.jQuery && window.jQuery.active == 0"));
    }
}
