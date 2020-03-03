package org.scadalts.e2e.test.impl.config;

import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.config.E2eConfigFromFileProvider;

public class TestImplConfigurator {

    private static E2eConfig CONFIG = new E2eConfigFromFileProvider().get();

    public static void init(E2eConfig config) {
        if(config == null) {
            return;
        }
        CONFIG = config;
        TestImplConfiguration.alarmListChangedAfterMs = config.getAlarmListChangedAfterMs();
        TestImplConfiguration.alarmListNoChangedAfterMs = config.getAlarmListNoChangedAfterMs();
        TestImplConfiguration.waitingAfterSetPointValueMs = config.getWaitingAfterSetPointValueMs();
        TestImplConfiguration.graphicalViewName = config.getGraphicalViewName();
        TestImplConfiguration.dataPointToChangeXid = config.getDataPointToChangeXid();
        TestImplConfiguration.dataPointToReadXid = config.getDataPointToReadXid();
        TestImplConfiguration.dataPointValuesToChangeTests = config.getDataPointValuesToTests();
        TestImplConfiguration.dataSourceName = config.getDataSourceName();
        TestImplConfiguration.dataPointName = config.getDataPointName();
        TestImplConfiguration.dataPointSourceXid = config.getDataPointSourceXid();
        TestImplConfiguration.dataPointTargetXid = config.getDataPointTargetXid();
        TestImplConfiguration.testPlan = config.getTestPlan();
        TestImplConfiguration.timeout = config.getTimeoutMs();
    }

    public static void init() {
        init(CONFIG);
    }
}
