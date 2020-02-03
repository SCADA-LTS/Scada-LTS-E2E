package org.scadalts.e2e.test.api.config;

import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.test.core.config.TestCoreConfiguration;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;

public class TestConfigurator {

    public static void init(E2eConfig config) {
        TestCoreConfiguration.testPlan = config.getTestPlan();
        TestCoreConfiguration.classesTestRefs = config.getClassesTestRefs();

        TestImplConfiguration.alarmListChangedAfterMs = config.getAlarmListChangedAfterMs();
        TestImplConfiguration.alarmListNoChangedAfterMs = config.getAlarmListNoChangedAfterMs();
        TestImplConfiguration.graphicalViewName = config.getGraphicalViewName();
        TestImplConfiguration.dataPointToChangeXid = config.getDataPointToChangeXid();
        TestImplConfiguration.dataPointToReadXid = config.getDataPointToReadXid();
    }
}
