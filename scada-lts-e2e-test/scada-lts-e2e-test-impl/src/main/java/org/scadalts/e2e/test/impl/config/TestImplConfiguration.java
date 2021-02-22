package org.scadalts.e2e.test.impl.config;

import org.scadalts.e2e.common.core.types.TestPlan;

public class TestImplConfiguration {

    public static int alarmListChangedAfterMs = 8001;
    public static int alarmListNoChangedAfterMs = 2001;
    public static int waitingAfterSetPointValueMs = 5001;
    public static String graphicalViewName = "gv_test";
    public static String dataPointToChangeXid = "dataPointToChangeXid";
    public static String dataPointToReadXid = "dataPointToReadXid";
    public static String[] dataPointValuesToChangeTests = new String[]{};
    public static String dataSourceName = "ds_test";
    public static String dataPointName = "dp_test";
    public static String dataPointSourceXid = "dataPointSourceXid";
    public static String dataPointTargetXid = "dataPointTargetXid";
    public static TestPlan[] testPlans = new TestPlan[]{TestPlan.ANY};
    public static long timeout = 6001;
    public static String dataSourceNameEventDetectorTest = "ds_event_detector_test";
    public static String watchListName = "wl_test";
}
