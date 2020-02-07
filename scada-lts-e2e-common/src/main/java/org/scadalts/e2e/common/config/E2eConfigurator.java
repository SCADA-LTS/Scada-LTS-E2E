package org.scadalts.e2e.common.config;

import org.apache.logging.log4j.core.config.Configurator;

public class E2eConfigurator {

    public static void init(E2eConfig config) {
        E2eConfiguration.authType = config.getAuthType();
        E2eConfiguration.baseUrl = config.getBaseUrl();
        E2eConfiguration.password = config.getPassword();
        E2eConfiguration.userName = config.getUserName();
        E2eConfiguration.logLevel = config.getLogLevel();

        Configurator.setRootLevel(E2eConfiguration.logLevel);
        Configurator.setAllLevels("org.apache.logging.log4j", E2eConfiguration.logLevel);
    }
}
