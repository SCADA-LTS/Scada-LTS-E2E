package org.scadalts.e2e.test.impl.config;

public class TestImplConfiguration {

    public volatile static int alarmListChangedAfterMs = 8001;
    public volatile static int alarmListNoChangedAfterMs = 2001;
    public volatile static int waitingAfterSetPointValueMs = 5001;
    public volatile static String graphicalViewName = "viewTest";
    public volatile static String dataPointToChangeXid = "dataPointToChangeXid";
    public volatile static String dataPointToReadXid = "dataPointToReadXid";
    public volatile static String[] dataPointValuesToChangeTests = new String[]{};
    public volatile static String dataSourceName = "dataSourceName";
    public volatile static String dataPointName = "dataPointName";
    public volatile static String dataPointSourceXid = "dataPointSourceXid";
    public volatile static String dataPointTargetXid = "dataPointTargetXid";
}
