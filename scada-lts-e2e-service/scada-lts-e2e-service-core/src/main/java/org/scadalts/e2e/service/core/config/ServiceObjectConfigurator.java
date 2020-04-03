package org.scadalts.e2e.service.core.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;

@Log4j2
public class ServiceObjectConfigurator {

    public static void init(E2eConfig config) {
        System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Log4jLogger");

    }

    public static void init(String sessionId) {
        logger.info("sessionId: {}", sessionId);
        ServiceObjectConfiguration.sessionId = sessionId;
    }
}
