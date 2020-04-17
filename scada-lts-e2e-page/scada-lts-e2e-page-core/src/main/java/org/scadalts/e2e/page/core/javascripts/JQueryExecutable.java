package org.scadalts.e2e.page.core.javascripts;

import org.scadalts.e2e.page.core.utils.PageStabilityUtil;

import java.util.Optional;

public interface JQueryExecutable extends JavascriptExecutable {

    default Optional<Object> executeJQuery(String jQueryScript) {
        waitLoadedJQuery();
        return executeJs(jQueryScript);
    }

    default String executeJQueryString(String jQueryScript) {
        Optional<Object> result = executeJQuery(jQueryScript);
        if(result.isPresent() && result.get() instanceof String)
            return String.valueOf(result.get());
        return "";
    }

    default void waitLoadedJQuery() {
        PageStabilityUtil.waitWhile(() -> !executeJsBoolean(()->"return !!window.jQuery && window.jQuery.active == 0"));
    }
}
