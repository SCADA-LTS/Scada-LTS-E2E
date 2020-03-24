package org.scadalts.e2e.page.core.javascripts;

public interface JavascriptProvider {

    String getScriptToExecute();

    default String executeInJava(String arg) {
        return arg;
    }
}
