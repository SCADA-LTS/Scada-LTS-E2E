package org.scadalts.e2e.page.core.config;

import com.codeborne.selenide.Configuration;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.config.Configurator;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.config.E2eConfigFromFileProvider;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.common.utils.FileUtil;
import org.scadalts.e2e.page.core.config.webdriver.WebDriverManualConfig;

@Log4j2
public class PageObjectConfigurator {

    private static E2eConfig CONFIG = new E2eConfigFromFileProvider().get();

    public static void init(E2eConfig config) {
        if(config == null) {
            _initDefault();
            logger.warn("Config is empty!");
            return;
        }
        CONFIG = config;

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

        PageConfiguration.ctrl = config.getCtrlCode();

        Configurator.setRootLevel(config.getLogLevel());
        Configurator.setAllLevels("org.apache.logging.log4j", config.getLogLevel());

        if(!config.isDriverManagerMode()) {
            _configureWebDriver(config);
        }
    }

    public static void init() {
        init(CONFIG);
    }

    private static void _configureWebDriver(E2eConfig config) {
        try {
            logger.info("web driver loading...  {}", config.getBrowserRef());
            WebDriverManualConfig manualConfig = WebDriverManualConfig.getWebDriverConfig(config);
            _setWebDriverOptions(manualConfig);
            String webDriverPath = _getWebDriverPath(config, manualConfig);
            System.setProperty(manualConfig.getWebDriverKey(), webDriverPath);
            PageConfiguration.driverFile = FileUtil.getFileFromJar(webDriverPath);

        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
        }
    }

    private static void _setWebDriverOptions(WebDriverManualConfig manualConfig) {
        manualConfig.setOptions();
    }

    private static String _getWebDriverPath(E2eConfig config, WebDriverManualConfig manualConfig) {
        return isPathFromConsole(config) ? manualConfig.getWebDriverPathConfig().getPath()
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