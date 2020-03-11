package org.scadalts.e2e.test.api;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.config.E2eConfigurator;
import org.scadalts.e2e.test.api.config.TestConfigurator;
import org.scadalts.e2e.test.core.plans.engine.E2eSummarable;
import org.scadalts.e2e.test.core.utils.TestResultPrinter;
import org.scadalts.e2e.test.core.plans.exec.TestsExecutable;

@Log4j2
class E2eTestApiImpl implements E2eTestApi {

    private final TestsExecutable executor;

    E2eTestApiImpl(TestsExecutable executor) {
        this.executor = executor;
    }

    @Override
    public E2eSummarable run(E2eConfig config) {
        E2eConfigurator.init(config);
        TestConfigurator.init(config);
        E2eSummarable summary = _execute(executor, config);
        TestResultPrinter.print(summary);
        return summary;
    }

    private static E2eSummarable _execute(TestsExecutable executor, E2eConfig config) {
        try {
            return executor.execute(config);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return E2eSummarable.empty();
        }
    }
}
