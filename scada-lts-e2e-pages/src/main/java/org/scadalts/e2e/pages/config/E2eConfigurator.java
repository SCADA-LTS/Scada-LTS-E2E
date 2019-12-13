package org.scadalts.e2e.pages.config;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.Keys;
import org.scadalts.e2e.config.E2eConfig;
import org.scadalts.e2e.config.E2eConfigKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class E2eConfigurator {

    private static final Logger logger = LoggerFactory.getLogger(E2eConfigurator.class);

    public static void init() {
        E2eConfig config = E2eConfig.INSTANCE;
        logger.info(config.toString());
        Configuration.headless = config.getBoolean(E2eConfigKeys.HEADLESS, true);
        Configuration.timeout = config.getInt(E2eConfigKeys.TIMEOUT, 6000);
        Configuration.baseUrl = config.getString(E2eConfigKeys.URL, "");
        Configuration.browser = config.getString(E2eConfigKeys.BROWSER, "chrome");
    }

    public static final String NEW_TAB_SHORTCUTS = Keys.chord(Keys.CONTROL,Keys.RETURN);
}
