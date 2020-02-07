package org.scadalts.e2e.common.config;

import org.scadalts.e2e.common.types.AuthType;
import org.scadalts.e2e.common.types.BrowserRef;
import org.scadalts.e2e.common.types.PageLoadStrategy;
import org.scadalts.e2e.common.types.TestPlan;

public interface E2eConfig {
    PageLoadStrategy getPageLoadStrategy();

    BrowserRef getBrowserRef();

    AuthType getAuthType();

    TestPlan getTestPlan();

    java.net.URL getBaseUrl();

    java.net.URL getReportsUrl();

    java.nio.file.Path getReportsFolder();

    java.io.File getDriverFile();

    String getUserName();

    String getPassword();

    String getHostProxy();

    String getGraphicalViewName();

    String getDataPointToChangeXid();

    String getDataPointToReadXid();

    String getCronPattern();

    String[] getClassesTestRefs();

    org.apache.logging.log4j.Level getLogLevel();

    org.apache.logging.log4j.Level getLogLevelApp();

    int getCtrlCode();

    int getTimeoutMs();

    int getPollingIntervalMs();

    int getAlarmListChangedAfterMs();

    int getAlarmListNoChangedAfterMs();

    int getWaitingAfterSetPointValueMs();

    int getPortProxy();

    int getPortApp();

    boolean isFastSetValueMode();

    boolean isProxyMode();

    boolean isHeadlessMode();

    boolean isScreenshotMode();

    boolean isDriverManagerMode();

    boolean isSchedulingMode();
/*
    static E2eConfig newConfig() {
        return new E2eConfigDefault();
    }*/

}
