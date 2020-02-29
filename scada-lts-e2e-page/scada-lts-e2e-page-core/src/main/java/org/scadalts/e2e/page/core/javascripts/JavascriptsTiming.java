package org.scadalts.e2e.page.core.javascripts;

public enum JavascriptsTiming implements JavascriptProvider {

    RESPONSE_START_MS("return window.performance.timing.responseStart"),
    DOM_COMPLETE_MS("return window.performance.timing.domComplete"),
    NAVIGATION_START_MS("return window.performance.timing.navigationStart");

    private String script;

    JavascriptsTiming(String script) {
        this.script = script;
    }

    @Override
    public String getScript() {
        return script;
    }
}
