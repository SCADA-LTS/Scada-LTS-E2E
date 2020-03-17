package org.scadalts.e2e.service.core.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.config.E2eConfiguration;

@Log4j2
public class ServiceObjectConfigurator {

    public static void init(E2eConfig config) {
        logger.info("sessionId: {}", E2eConfiguration.sessionId);
       // ServiceObjectConfiguration.sessionId = E2eConfiguration.sessionId;
        System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Log4jLogger");
    }
}
