package org.scadalts.e2e.webservice.api;

import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.webservice.core.config.E2eWebServiceObjectConfigurator;

class E2EWebServiceObjectApiImpl implements E2eWebServiceObjectApi {

    private final String sessionId;

    public E2EWebServiceObjectApiImpl(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void init(E2eConfig config) {
        E2eWebServiceObjectConfigurator.init(config, sessionId);
    }
}
