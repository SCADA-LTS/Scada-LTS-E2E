package org.scadalts.e2e.test.impl.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.ConfigHandler;
import org.scadalts.e2e.common.config.E2eConfig;

import java.util.Objects;

@Log4j2
public class TestImplConfigurator {

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
        TestImplConfiguration.alarmListChangedAfterMs = config.getAlarmListChangedAfterMs();
        TestImplConfiguration.alarmListNoChangedAfterMs = config.getAlarmListNoChangedAfterMs();
        TestImplConfiguration.waitingAfterSetPointValueMs = config.getWaitingAfterSetPointValueMs();
        TestImplConfiguration.graphicalViewName = config.getGraphicalViewName();
        TestImplConfiguration.dataPointToChangeXid = config.getDataPointToChangeXid();
        TestImplConfiguration.dataPointToReadXid = config.getDataPointToReadXid();
        TestImplConfiguration.dataPointValuesToChangeTests = config.getPointValuesToTests();
        TestImplConfiguration.dataSourceName = config.getDataSourceName();
        TestImplConfiguration.dataPointName = config.getDataPointName();
        TestImplConfiguration.dataPointSourceXid = config.getDataPointSourceXid();
        TestImplConfiguration.dataPointTargetXid = config.getDataPointTargetXid();
        TestImplConfiguration.testPlan = config.getTestPlan();
        TestImplConfiguration.timeout = config.getTimeoutMs();
    }

    public static void init() {
        init(ConfigHandler.getConfig());
    }
}
