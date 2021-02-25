package org.scadalts.e2e.page.api;

import org.scadalts.e2e.common.core.config.E2eConfig;

public interface E2ePageObjectApi {
    void init(E2eConfig config);

    static E2ePageObjectApi newInstance() {
        return new E2ePageObjectApiImpl();
    }
}
