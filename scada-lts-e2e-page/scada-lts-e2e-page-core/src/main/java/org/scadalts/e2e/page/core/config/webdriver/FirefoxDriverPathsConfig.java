package org.scadalts.e2e.page.core.config.webdriver;

import lombok.Getter;

@Getter
public enum FirefoxDriverPathsConfig implements WebDrvierPathsConfig {

    WINDOWS_32("win", "webdriver/firefox/geckodriver-v0.26.0-win64/geckodriver.exe", "geckodriver.log"),
    MAC_64("mac", "webdriver/firefox/geckodriver-v0.26.0-macos/geckodriver", "geckodriver.log"),
    LINUX_64("nux", "webdriver/firefox/geckodriver-v0.26.0-linux64/geckodriver", "geckodriver.log");

    private String systemKey;
    private String path;
    private String logFilePath;

    FirefoxDriverPathsConfig(String systemKey, String webDriverPath, String logFilePath) {
        this.systemKey = systemKey;
        this.path = webDriverPath;
    }

    public static WebDrvierPathsConfig getConfig() {
        return GetConfigForSystem.getConfig(FirefoxDriverPathsConfig.values(),
                FirefoxDriverPathsConfig.WINDOWS_32);
    }
}
