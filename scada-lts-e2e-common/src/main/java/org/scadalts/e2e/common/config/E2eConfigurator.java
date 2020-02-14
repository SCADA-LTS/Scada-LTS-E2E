package org.scadalts.e2e.common.config;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.Objects;

@Log4j2
public class E2eConfigurator {

    private static E2eConfig CONFIG = new E2eConfigFromFileProvider().get();

    public static void init(E2eConfig config) {
        if(Objects.isNull(config)) {
            logger.warn("Config is empty");
            return;
        }
        CONFIG = config;
        E2eConfiguration.authType = config.getAuthType();
        E2eConfiguration.baseUrl = config.getUrlAppBeingTested();
        E2eConfiguration.password = config.getPassword();
        E2eConfiguration.userName = config.getUserName();
        E2eConfiguration.logLevel = config.getLogLevel();

        Configurator.setRootLevel(E2eConfiguration.logLevel);
        Configurator.setAllLevels("org.apache.logging.log4j", E2eConfiguration.logLevel);
    }

    public static void init() {
        init(CONFIG);
    }

}
