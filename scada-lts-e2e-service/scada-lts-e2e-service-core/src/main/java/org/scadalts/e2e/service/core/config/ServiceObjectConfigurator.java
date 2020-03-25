package org.scadalts.e2e.service.core.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.config.E2eConfigFromFileProvider;

@Log4j2
public class ServiceObjectConfigurator {


    private static E2eConfig CONFIG = new E2eConfigFromFileProvider().get();

    public static void setSessionId(E2eConfig config) {
        if (config == null) {
            logger.warn("Config is empty!");
            return;
        }
        CONFIG = config;
        System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Log4jLogger");

    }

    public static void setSessionId(String sessionId) {
        logger.info("sessionId: {}", sessionId);
        ServiceObjectConfiguration.sessionId = sessionId;
    }
}
