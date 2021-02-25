package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.common.core.config.E2eConfig;
import org.scadalts.e2e.test.core.plans.exec.TestsTerminable;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;

public class TestsTerminator implements TestsTerminable {

    @Override
    public void terminate(E2eConfig config) {
        CriteriaRegisterAggregator criteriaRegisterAggregator = CriteriaRegisterAggregator.INSTANCE;

    }
}
