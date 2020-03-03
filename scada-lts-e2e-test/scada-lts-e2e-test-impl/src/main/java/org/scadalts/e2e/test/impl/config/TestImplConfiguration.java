package org.scadalts.e2e.test.impl.config;

import org.scadalts.e2e.common.types.TestPlan;

public class TestImplConfiguration {

    public static int alarmListChangedAfterMs = 8001;
    public static int alarmListNoChangedAfterMs = 2001;
    public static int waitingAfterSetPointValueMs = 5001;
    public static String graphicalViewName = "viewTest";
    public static String dataPointToChangeXid = "dataPointToChangeXid";
    public static String dataPointToReadXid = "dataPointToReadXid";
    public static String[] dataPointValuesToChangeTests = new String[]{};
    public static String dataSourceName = "dataSourceName";
    public static String dataPointName = "dataPointName";
    public static String dataPointSourceXid = "dataPointSourceXid";
    public static String dataPointTargetXid = "dataPointTargetXid";
    public static TestPlan testPlan = TestPlan.ANY;
    public static long timeout = 6001;
}
