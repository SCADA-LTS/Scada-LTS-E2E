package org.scadalts.e2e.page.core.config.webdriver;

import lombok.Getter;

@Getter
public enum ChromeDriverPathsConfig implements WebDrvierPathsConfig {

    WINDOWS_32("win", "webdriver/chrome/chromedriver_win32/chromedriver.exe"),
    MAC_64("mac", "webdriver/chrome/chromedriver_mac64/chromedriver"),
    LINUX_64("nux", "webdriver/chrome/chromedriver_linux64/chromedriver");

    private String systemKey;
    private String path;

    ChromeDriverPathsConfig(String systemKey, String webDriverPath) {
        this.systemKey = systemKey;
        this.path = webDriverPath;
    }

    public static WebDrvierPathsConfig getConfig() {
        return GetConfigForSystem.getConfig(ChromeDriverPathsConfig.values(),
                        ChromeDriverPathsConfig.WINDOWS_32);
    }
}
