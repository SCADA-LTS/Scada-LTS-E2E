package org.scadalts.e2e.tests.plan;

import org.scadalts.e2e.tests.config.E2ePlanTestsKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PlanTestsExecutor {

    INSTANCE;

    private final PlanTestsProvider testsProvider = PlanTestsProvider.INSTANCE;
    private final ScadaTestRunner testRunner = ScadaTestRunner.INSTANCE;
    private static final Logger logger = LoggerFactory.getLogger(PlanTestsExecutor.class);

    public void execute(Properties ref) {
        List<Class<?>> tests = testsProvider.getPlan(ref);
        if(tests.isEmpty()) {
            logger.warn("Enter at least one of the parameters to run the tests: {} \n" +
                    " The following parameters have been introduced: {}", keys(), ref);
            return;
        }
        testRunner.runAndPrint(tests);
    }

    private Set<String> keys() {
        return Stream.of(E2ePlanTestsKeys.values()).map(E2ePlanTestsKeys::key).collect(Collectors.toSet());
    }
}
