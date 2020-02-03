package org.scadalts.e2e.page.core.pages;

import org.scadalts.e2e.page.core.javascript.JavascriptExecutable;
import org.scadalts.e2e.page.core.javascript.JavascriptsTiming;

interface PageLoadingTimeMeasurable<T extends PageObject<T>> extends JavascriptExecutable {

    T printLoadingMeasure();
    T printLoadingMeasure(String msg);

    default long frontendPerformanceMs() {
        long responseStart = getResponseStartMs();
        long domComplete = getDomCompleteMs();
        if(responseStart > -1 && domComplete > -1)
            return domComplete - responseStart;
        return responseStart <= -1 ? responseStart : domComplete;
    }

    default long backendPerformanceMs() {
        long responseStart = getResponseStartMs();
        long navigationStart = getNavigationStartMs();
        if(responseStart > -1 && navigationStart > -1)
            return responseStart - navigationStart;
        return responseStart <= -1 ? responseStart : navigationStart;
    }

    default long getResponseStartMs() {
        return getTimingMs(JavascriptsTiming.RESPONSE_START_MS);
    }

    default long getDomCompleteMs() {
        return getTimingMs(JavascriptsTiming.DOM_COMPLETE_MS);
    }

    default long getNavigationStartMs() {
        return getTimingMs(JavascriptsTiming.NAVIGATION_START_MS);
    }

    default long getTimingMs(JavascriptsTiming script) {
        return executeJsLong(script);
    }
}
