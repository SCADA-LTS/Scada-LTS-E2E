package org.scadalts.e2e.service.api;

import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.service.core.config.ServiceObjectConfigurator;

class ServiceObjectApiImpl implements ServiceObjectApi {

    private final String sessionId;

    public ServiceObjectApiImpl(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void init(E2eConfig config) {
        ServiceObjectConfigurator.setSessionId(config);
    }
}
