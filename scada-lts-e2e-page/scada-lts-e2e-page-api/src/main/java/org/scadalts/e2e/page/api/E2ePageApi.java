package org.scadalts.e2e.page.api;

import org.scadalts.e2e.common.config.E2eConfig;

public interface E2ePageApi {
    void init(E2eConfig config);

    static E2ePageApi newInstance() {
        return new E2ePageApiImpl();
    }
}
