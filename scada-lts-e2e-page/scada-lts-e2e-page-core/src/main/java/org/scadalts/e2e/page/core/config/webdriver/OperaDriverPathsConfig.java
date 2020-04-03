package org.scadalts.e2e.page.core.config.webdriver;

import lombok.Getter;

@Getter
public enum OperaDriverPathsConfig implements WebDrvierPathsConfig {

    WINDOWS_32("win", "webdriver/opera/operadriver_win64/operadriver.exe", ""),
    MAC_64("mac", "webdriver/opera/operadriver_mac64/operadriver", ""),
    LINUX_64("nux", "webdriver/opera/operadriver_linux64/operadriver", "");

    private String systemKey;
    private String path;
    private String logFilePath;

    OperaDriverPathsConfig(String systemKey, String webDriverPath, String logFilePath) {
        this.systemKey = systemKey;
        this.path = webDriverPath;
    }

    public static WebDrvierPathsConfig getConfig() {
        return WebDrvierPathsConfig.getDriverPathConfig(OperaDriverPathsConfig.values(),
                OperaDriverPathsConfig.WINDOWS_32);
    }
}
