package org.scadalts.e2e.service.api;

import org.scadalts.e2e.common.config.E2eConfig;

public interface ServiceObjectApi {

    void init(E2eConfig config);

    static ServiceObjectApi newInstance(String sessionId) {
        return new ServiceObjectApiImpl(sessionId);
    }
}
