package org.scadalts.e2e.page.core.config.webdriver;

public enum OperaDriverPathConfig implements WebDrvierPathConfig {

    WINDOWS_32("win", "webdriver/opera/operadriver_win64/operadriver.exe"),
    MAC_64("mac", "webdriver/opera/operadriver_mac64/operadriver"),
    LINUX_64("nux", "webdriver/opera/operadriver_linux64/operadriver");

    private String systemKey;
    private String webDriverPath;

    OperaDriverPathConfig(String systemKey, String webDriverPath) {
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
        return WebDrvierPathConfig.getDriverPathConfig(OperaDriverPathConfig.values(),
                OperaDriverPathConfig.WINDOWS_32);
    }
}
