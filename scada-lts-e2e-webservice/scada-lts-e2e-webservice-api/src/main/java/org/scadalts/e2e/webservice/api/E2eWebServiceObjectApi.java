package org.scadalts.e2e.webservice.api;

import org.scadalts.e2e.common.config.E2eConfig;

public interface E2eWebServiceObjectApi {

    void init(E2eConfig config);

    static E2eWebServiceObjectApi newInstance(String sessionId) {
        return new E2EWebServiceObjectApiImpl(sessionId);
    }
}
