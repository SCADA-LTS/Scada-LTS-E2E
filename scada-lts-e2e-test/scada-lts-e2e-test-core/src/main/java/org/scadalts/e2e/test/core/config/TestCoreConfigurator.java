package org.scadalts.e2e.test.core.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.ConfigHandler;
import org.scadalts.e2e.common.config.E2eConfig;

import java.util.Objects;

@Log4j2
public class TestCoreConfigurator {

    private static E2eConfig CONFIG;

    public static void init(E2eConfig config) {
        if(!Objects.isNull(CONFIG)) {
            return;
        }
        if(Objects.isNull(config)) {
            logger.warn("Config is null");
            return;
        }
        CONFIG = ConfigHandler.handle(config);
        TestCoreConfiguration.testPlans = config.getTestPlans();
        TestCoreConfiguration.classesTestRefs = config.getClassesTestRefs();
    }

    public static void init() {
        init(ConfigHandler.getConfig());
    }
}
