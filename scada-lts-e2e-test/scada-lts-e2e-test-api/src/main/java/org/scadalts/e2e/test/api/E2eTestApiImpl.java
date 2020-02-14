package org.scadalts.e2e.test.api;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.config.E2eConfigurator;
import org.scadalts.e2e.test.api.config.TestConfigurator;
import org.scadalts.e2e.test.core.plan.exec.TestsExecutable;
import org.scadalts.e2e.test.core.plan.runner.E2eResultSummary;

@Log4j2
class E2eTestApiImpl implements E2eTestApi {

    private final TestsExecutable executor;

    E2eTestApiImpl(TestsExecutable executor) {
        this.executor = executor;
    }

    @Override
    public E2eResultSummary run(E2eConfig config) {
        E2eConfigurator.init(config);
        TestConfigurator.init(config);
        return _execute(executor, config);
    }

    private static E2eResultSummary _execute(TestsExecutable executor, E2eConfig config) {
        try {
            return executor.execute(config);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return E2eResultSummary.empty();
        }
    }
}
