package org.scadalts.e2e.page.impl.groovy;

public class ConfigurationUtil {

    public static void headless(boolean headless) {
        com.codeborne.selenide.Configuration.headless = headless;
    }

    public static void path(String reportsFolder) {
        com.codeborne.selenide.Configuration.reportsUrl = reportsFolder;
    }

}
