package org.scadalts.e2e.service.core.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;

@Log4j2
public class ServiceObjectConfigurator {

    public static void init(E2eConfig config, String sessionId) {
        logger.info("sessionId: {}", sessionId);
        //WebServiceObjectConfiguration.sessionId = sessionId;
    }
}
