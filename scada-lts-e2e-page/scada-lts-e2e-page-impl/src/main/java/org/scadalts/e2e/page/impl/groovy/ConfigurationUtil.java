package org.scadalts.e2e.page.impl.groovy;

public final class ConfigurationUtil {

    private static boolean PAGE_MODE = true;

    private ConfigurationUtil() {}

    public static void headless(boolean headless) {
        com.codeborne.selenide.Configuration.headless = headless;
        if(!headless)
            PAGE_MODE = true;
    }

    public static void path(String reportsFolder) {
        com.codeborne.selenide.Configuration.reportsUrl = reportsFolder;
    }

    public static void pageMode(boolean pageMode) {
        PAGE_MODE = pageMode;
    }

    public static boolean isPageMode() {
        return PAGE_MODE;
    }
}
