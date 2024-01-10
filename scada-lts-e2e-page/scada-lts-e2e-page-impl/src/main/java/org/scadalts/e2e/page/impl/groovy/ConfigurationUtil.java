package org.scadalts.e2e.page.impl.groovy;

import com.codeborne.selenide.Configuration;
import org.scadalts.e2e.common.core.config.E2eConfiguration;

import java.net.MalformedURLException;
import java.net.URL;

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

    public static void baseUrl(String baseUrl) {
        Configuration.baseUrl = baseUrl;
        try {
            E2eConfiguration.baseUrl = new URL(baseUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loginDisabled(boolean disabledLogin) {
        E2eConfiguration.loginDisabled = disabledLogin;
    }
}
