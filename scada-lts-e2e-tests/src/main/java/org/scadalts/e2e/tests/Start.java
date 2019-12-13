package org.scadalts.e2e.tests;

import org.scadalts.e2e.config.E2eConfig;
import org.scadalts.e2e.config.E2eConfigKeys;

import org.scadalts.e2e.tests.plan.PlanTestsExecutor;
import org.scadalts.e2e.tests.utils.ConsoleArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


public class Start {

    private static final Logger logger = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) {
        Properties console = ConsoleArgs.toProperties(args);
        E2eConfig config = E2eConfig.INSTANCE.updateConfig(console);
        if(console.containsKey(E2eConfigKeys.CONFIG.key())) {
            logger.info(config.toString());
            return;
        }
        PlanTestsExecutor.INSTANCE.execute(console);
    }

}
