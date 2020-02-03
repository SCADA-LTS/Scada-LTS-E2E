package org.scadalts.e2e.webservice.core.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;

@Log4j2
public class E2eWebServiceObjectConfigurator {

    public static void init(E2eConfig config, String sessionId) {
        logger.debug("sessionId: {}", sessionId);
        WebServiceObjectConfiguration.sessionId = sessionId;
    }
}
