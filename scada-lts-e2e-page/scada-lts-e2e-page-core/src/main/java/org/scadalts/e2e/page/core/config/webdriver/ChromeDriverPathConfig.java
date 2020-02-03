package org.scadalts.e2e.page.core.config.webdriver;

public enum ChromeDriverPathConfig implements WebDrvierPathConfig {

    WINDOWS_32("win", "webdriver/chrome/chromedriver_win32/chromedriver.exe"),
    MAC_64("mac", "webdriver/chrome/chromedriver_mac64/chromedriver"),
    LINUX_64("nux", "webdriver/chrome/chromedriver_linux64/chromedriver");

    private String systemKey;
    private String webDriverPath;

    ChromeDriverPathConfig(String systemKey, String webDriverPath) {
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
        return WebDrvierPathConfig.getDriverPathConfig(ChromeDriverPathConfig.values(),
                        ChromeDriverPathConfig.WINDOWS_32);
    }
}
