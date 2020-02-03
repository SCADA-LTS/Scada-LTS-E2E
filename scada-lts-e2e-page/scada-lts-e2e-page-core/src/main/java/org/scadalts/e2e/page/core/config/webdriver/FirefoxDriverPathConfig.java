package org.scadalts.e2e.page.core.config.webdriver;


public enum FirefoxDriverPathConfig implements WebDrvierPathConfig {

    WINDOWS_32("win", "webdriver/firefox/geckodriver-v0.26.0-win64/geckodriver.exe"),
    MAC_64("mac", "webdriver/firefox/geckodriver-v0.26.0-macos/geckodriver"),
    LINUX_64("nux", "webdriver/firefox/geckodriver-v0.26.0-linux64/geckodriver");

    private String systemKey;
    private String webDriverPath;

    FirefoxDriverPathConfig(String systemKey, String webDriverPath) {
        this.systemKey = systemKey;
        this.webDriverPath = webDriverPath;
    }

    @Override
    public String getPath() {
        return webDriverPath;
    }

    @Override
    public String getSystemKey() {
        return systemKey;
    }

    public static WebDrvierPathConfig getConfig() {
        return WebDrvierPathConfig.getDriverPathConfig(FirefoxDriverPathConfig.values(),
                FirefoxDriverPathConfig.WINDOWS_32);
    }
}
