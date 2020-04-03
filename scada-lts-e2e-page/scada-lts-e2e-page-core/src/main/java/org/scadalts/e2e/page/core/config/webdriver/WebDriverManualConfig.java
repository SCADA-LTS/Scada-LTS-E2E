package org.scadalts.e2e.page.core.config.webdriver;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
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
            "webdriver.chrome.driver",
            "webdriver.chrome.logfile",
            "webdriver.chrome.verboseLogging",
            ChromeDriverPathsConfig.getConfig(),
            ChromeOptions.CAPABILITY) {

        @Override
        public void setOptions(E2eConfig config) {
            System.setProperty("chromeoptions.args", WebDriverManualConfig.StabChromeOptions.all());
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

    private static String getBrowserLogLevel(E2eConfig config) {
        return MessageFormat.format("'{level: '{0}'}'",
                config.getLogLevel() == Level.DEBUG ? "debug" : "info");
    }

    @Getter
    public enum StabChromeOptions {
        PROXY_SERVER("--proxy-server","='direct://'"),
        PROXY_BYPASS_LIST("--proxy-bypass-list","=*"),
        NO_PROXY_SERVER("--no-proxy-server",""),
        NO_SANDBOX("--no-sandbox", ""),
        DISABLE_SETUID_SANDBOX("--disable-setuid-sandbox", ""),
        VERBOSE("--verbose", ""),
        DISABLE_DEV_SHM_USAGE("--disable-dev-shm-usage", "");

        private final String option;
        private final String value;

        StabChromeOptions(String option, String value) {
            this.option = option;
            this.value = value;
        }

        public static String[] array() {
            return Stream.of(StabChromeOptions.values())
                    .map(a -> a.option + a.value)
                    .toArray(String[]::new);
        }

        public static String all() {
            return Stream.of(StabChromeOptions.values())
                    .map(a -> a.option + a.value)
                    .collect(Collectors.joining(","));
        }
    }
}
