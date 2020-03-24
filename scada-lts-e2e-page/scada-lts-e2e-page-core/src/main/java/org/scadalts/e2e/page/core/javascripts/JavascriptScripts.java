package org.scadalts.e2e.page.core.javascripts;

public enum JavascriptScripts implements JavascriptProvider {
    READY_STATE("return document.readyState");

    private String script;

    JavascriptScripts(String script) {
        this.script = script;
    }

    @Override
    public String getScriptToExecute() {
        return script;
    }
}
