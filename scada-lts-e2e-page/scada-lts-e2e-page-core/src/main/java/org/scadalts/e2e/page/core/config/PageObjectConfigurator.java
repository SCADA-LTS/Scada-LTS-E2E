package org.scadalts.e2e.page.core.config;

import com.codeborne.selenide.Configuration;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.scadalts.e2e.common.config.ConfigHandler;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.common.config.E2eConstant;
import org.scadalts.e2e.common.utils.FileUtil;
import org.scadalts.e2e.page.core.config.webdriver.WebDriverManualConfig;

import java.util.Objects;

@Log4j2
public class PageObjectConfigurator {

    private static E2eConfig CONFIG;

    public static void init(E2eConfig config) {
        if(!Objects.isNull(CONFIG)) {
            return;
        }
        if(Objects.isNull(config)) {
            logger.warn("Config is null");
            return;
        }
        CONFIG = ConfigHandler.handle(config);
        Configuration.timeout = config.getTimeoutMs();
        Configuration.baseUrl = config.getUrlAppBeingTested().toString();
        Configuration.browser = config.getBrowserRef().name().toLowerCase();
        Configuration.pageLoadStrategy = config.getPageLoadStrategy().name().toLowerCase();
        Configuration.driverManagerEnabled = config.isDriverManagerMode();
        Configuration.screenshots = config.isScreenshotMode();
        Configuration.headless = config.isHeadlessMode();
        Configuration.reportsUrl = config.getReportsUrl() == null ? null : config.getReportsUrl().toString();
        Configuration.reportsFolder = config.getReportsFolder() == null ? null : config.getReportsFolder().toString();
        Configuration.fastSetValue = config.isFastSetValueMode();
        Configuration.pollingInterval = config.getPollingIntervalMs();
        Configuration.proxyEnabled = config.isProxyMode();
        Configuration.proxyHost = config.getHostProxy();
        Configuration.proxyPort = config.getPortProxy();
        PageConfiguration.timeout = config.getTimeoutMs();

        Configurator.setRootLevel(config.getLogLevel());
        Configurator.setAllLevels("org.apache.logging.log4j", config.getLogLevel());
        Configurator.setAllLevels("java.util.logging", config.getLogLevel());

        _configureWebDriver(config);
    }

    private static void _configureWebDriver(E2eConfig config) {
        logger.info("web driver loading... {}", config.getBrowserRef());
        WebDriverManualConfig manualConfig = WebDriverManualConfig.getWebDriverConfig(config);

        if(config.getLogLevel() == Level.DEBUG) {
            System.setProperty(manualConfig.getLogFileKey(), E2eConstant.WEB_DRIVER_LOG_FILE);
            System.setProperty(manualConfig.getVerboseLoggingKey(), "true");
        }

        if(!config.isDriverManagerMode()) {
            String webDriverPath = _getWebDriverPath(config, manualConfig);
            _configureWebDriverPath(webDriverPath, manualConfig);
        }

        manualConfig.setOptions(config);
    }

    public static void init() {
        init(ConfigHandler.getConfig());
    }

    private static void _configureWebDriverPath(String webDriverPath, WebDriverManualConfig manualConfig) {
        try {
            logger.info("web driver file loading for... {}", manualConfig.getBrowserName());
            System.setProperty(manualConfig.getWebDriverKey(), webDriverPath);
            PageConfiguration.driverFile = FileUtil.getFileFromJar(webDriverPath);
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
        }
    }

    private static String _getWebDriverPath(E2eConfig config, WebDriverManualConfig manualConfig) {
        return isPathFromConsole(config) ? manualConfig.getWebDriverPathsConfig().getPath()
                : config.getDriverFile().getPath();
    }

    private static boolean isPathFromConsole(E2eConfig config) {
        return config.getDriverFile() != null && StringUtils.isNotBlank(config.getDriverFile().getAbsolutePath());
    }

    private static void _initDefault() {
        Configuration.timeout = 6001;
        Configuration.baseUrl = E2eConfiguration.baseUrl.toString();
        Configuration.browser = "chrome";
        Configuration.pageLoadStrategy = "normal";
        Configuration.driverManagerEnabled = false;
        Configuration.screenshots = true;
        Configuration.headless = false;
        Configuration.reportsUrl = "";
        Configuration.reportsFolder = "reports/";
        Configuration.fastSetValue = true;
        Configuration.pollingInterval = 201;
        Configuration.proxyEnabled = false;
        Configuration.proxyHost = "";
        Configuration.proxyPort = 1234;
    }
}
