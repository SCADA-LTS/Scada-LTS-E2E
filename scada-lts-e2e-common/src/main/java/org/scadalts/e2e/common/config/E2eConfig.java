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

    String getProxyHost();

    String getGraphicalViewName();

    String getDataPointToChangeXid();

    String getDataPointToReadXid();

    String[] getClassesTestRefs();

    org.apache.logging.log4j.Level getLogLevel();

    int getCtrlCode();

    int getTimeoutMs();

    int getPollingIntervalMs();

    int getAlarmListChangedAfterMs();

    int getAlarmListNoChangedAfterMs();

    int getWaitingAfterSetPointValueMs();

    int getProxyPort();

    boolean isFastSetValueEnabled();

    boolean isProxyEnabled();

    boolean isHeadlessModeEnabled();

    boolean isScreenshotEnabled();

    boolean isDriverManagerEnabled();
}
