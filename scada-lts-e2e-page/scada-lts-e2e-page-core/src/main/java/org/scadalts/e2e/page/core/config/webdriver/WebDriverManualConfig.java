package org.scadalts.e2e.page.core.config.webdriver;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.scadalts.e2e.common.config.E2eConfig;

import java.text.MessageFormat;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Getter
public enum WebDriverManualConfig {

    CHROME("chrome",
            ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
            ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY,
            ChromeDriverService.CHROME_DRIVER_VERBOSE_LOG_PROPERTY,
            ChromeDriverPathsConfig.getConfig(),
            ChromeOptions.CAPABILITY) {

        @Override
        public void setOptions(E2eConfig config) {
            if(config.getBrowserOptionsArgs().length > 0 && StringUtils.isNotBlank(config.getBrowserOptionsArgs()[0]))
                System.setProperty("chromeoptions.args", joinWithSeparator(config.getBrowserOptionsArgs(), ","));
            if(config.getBrowserOptionsPrefs().length > 0 && StringUtils.isNotBlank(config.getBrowserOptionsPrefs()[0]))
                System.setProperty("chromeoptions.prefs", joinWithSeparator(config.getBrowserOptionsPrefs(), ","));
        }

    },
    FIREFOX("firefox",
            "webdriver.gecko.driver",
            "webdriver.firefox.logfile",
            "webdriver.gecko.verboseLogging",
            FirefoxDriverPathsConfig.getConfig(),
            FirefoxOptions.FIREFOX_OPTIONS) {

        @Override
        public void setOptions(E2eConfig config) {
        }

    },
    OPERA("opera",
            "webdriver.opera.driver",
            "webdriver.opera.logfile?",
            "webdriver.opera.verboseLogging",
            OperaDriverPathsConfig.getConfig(),
            OperaOptions.CAPABILITY) {

        @Override
        public void setOptions(E2eConfig config) {
        }

    };

    public String LOG_FILE = "webdriver.log";

    private String browserName;
    private String webDriverKey;
    private String logFileKey;
    private String verboseLoggingKey;
    private WebDrvierPathsConfig webDriverPathsConfig;
    private String capabilityKey;

    WebDriverManualConfig(String browserName, String webDriverKey, String logFileKey,
                          String verboseLoggingKey, WebDrvierPathsConfig config,
                          String capabilityKey) {
        this.browserName = browserName;
        this.webDriverKey = webDriverKey;
        this.logFileKey = logFileKey;
        this.verboseLoggingKey = verboseLoggingKey;
        this.webDriverPathsConfig = config;
        this.capabilityKey = capabilityKey;
    }

    public abstract void setOptions(E2eConfig config);

    public static WebDriverManualConfig getWebDriverConfig(E2eConfig config) {
        return Stream.of(WebDriverManualConfig.values())
                .filter(a -> a.getBrowserName().equalsIgnoreCase(config.getBrowserRef().name()))
                .findFirst().orElse(WebDriverManualConfig.CHROME);
    }

    public static String joinWithSeparator(String[] args, String separator) {
        return Stream.of(args).collect(Collectors.joining(separator));
    }

    private static String getBrowserLogLevel(E2eConfig config) {
        return MessageFormat.format("'{level: '{0}'}'",
                config.getLogLevel() == Level.DEBUG ? "debug" : "info");
    }
}
