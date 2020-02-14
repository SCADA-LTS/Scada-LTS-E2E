package org.scadalts.e2e.test.core.config;

import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.config.E2eConfigFromFileProvider;

import java.util.Objects;

public class TestCoreConfigurator {

    private static E2eConfig CONFIG = new E2eConfigFromFileProvider().get();

    public static void init(E2eConfig config) {
        if (Objects.isNull(config)) {
            return;
        }
        CONFIG = config;
        TestCoreConfiguration.testPlan = config.getTestPlan();
        TestCoreConfiguration.classesTestRefs = config.getClassesTestRefs();
    }

    public static void init() {
        init(CONFIG);
    }
}
