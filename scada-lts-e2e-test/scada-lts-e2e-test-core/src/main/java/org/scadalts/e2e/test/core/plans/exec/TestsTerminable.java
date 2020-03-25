package org.scadalts.e2e.test.core.plans.exec;

import org.scadalts.e2e.common.config.E2eConfig;

@FunctionalInterface
public interface TestsTerminable {
    void terminate(E2eConfig config);
}
