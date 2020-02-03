package org.scadalts.e2e.test.api;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.test.core.plan.exec.TestsExecutable;

@Log4j2
class E2eTestApiImpl implements E2eTestApi {

    private final TestsExecutable executor;
    private final E2eConfig config;

    E2eTestApiImpl(TestsExecutable executor, E2eConfig config) {
        this.executor = executor;
        this.config = config;
    }

    @Override
    public boolean run() {
        return _execute(config);
    }

    private boolean _execute(E2eConfig config) {
        try {
            return executor.execute(config);
        } catch (Exception ex) {
            logger.warn(ex.getMessage(), ex);
            return false;
        }
    }
}
