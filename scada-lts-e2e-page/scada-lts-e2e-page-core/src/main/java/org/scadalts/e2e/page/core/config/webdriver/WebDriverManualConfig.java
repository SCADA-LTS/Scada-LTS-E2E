package org.scadalts.e2e.page.core.config.webdriver;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.scadalts.e2e.common.config.E2eConfig;

import java.util.stream.Stream;

@Log4j2
@Getter
public enum WebDriverManualConfig {

    CHROME("chrome", "webdriver.chrome.driver",
            ChromeDriverPathConfig.getConfig(),
            ChromeOptions.CAPABILITY) {

        @Override
        public void setOptions() {
            System.setProperty("chromeoptions.args", "--proxy-server='direct://',--proxy-bypass-list=*,--no-proxy-server");
        }

    },
    FIREFOX("firefox", FirefoxDriver.SystemProperty.DRIVER_XPI_PROPERTY,
            FirefoxDriverPathConfig.getConfig(),
            FirefoxOptions.FIREFOX_OPTIONS) {

        @Override
        public void setOptions() {

        }

    },
    OPERA("opera", "webdriver.opera.driver",
            OperaDriverPathConfig.getConfig(),
            OperaOptions.CAPABILITY) {

        @Override
        public void setOptions() {

        }

    };

    private String browserName;
    private String webDriverKey;
    private WebDrvierPathConfig webDriverPathConfig;
    private String capabilityKey;

    WebDriverManualConfig(String browserName, String webDriverKey, WebDrvierPathConfig config, String capabilityKey) {
        this.browserName = browserName;
        this.webDriverKey = webDriverKey;
        this.webDriverPathConfig = config;
        this.capabilityKey = capabilityKey;
    }

    public abstract void setOptions();

    public static WebDriverManualConfig getWebDriverConfig(E2eConfig config) {
        return Stream.of(WebDriverManualConfig.values())
                .filter(a -> a.getBrowserName().equalsIgnoreCase(config.getBrowserRef().name()))
                .findFirst().orElse(WebDriverManualConfig.CHROME);
    }
}
