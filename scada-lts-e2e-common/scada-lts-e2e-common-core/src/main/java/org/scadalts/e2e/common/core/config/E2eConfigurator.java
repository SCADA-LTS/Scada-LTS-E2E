package org.scadalts.e2e.common.core.config;

import lombok.extern.log4j.Log4j2;

import java.util.Objects;

@Log4j2
public class E2eConfigurator {

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
        E2eConfiguration.authType = config.getAuthType();
        E2eConfiguration.baseUrl = config.getUrlAppBeingTested();
        E2eConfiguration.password = config.getPassword();
        E2eConfiguration.username = config.getUserName();
        E2eConfiguration.logLevel = config.getLogLevel();
        E2eConfiguration.checkAuthentication = config.isCheckAuthentication();
        E2eConfiguration.loginDisabled = config.isLoginDisabled();
        org.apache.logging.log4j.core.config.Configurator.setRootLevel(config.getLogLevel());
        org.apache.logging.log4j.core.config.Configurator.setAllLevels("org.apache.logging.log4j", config.getLogLevel());
    }

    public static void init() {
        init(ConfigHandler.getConfig());
    }
}
