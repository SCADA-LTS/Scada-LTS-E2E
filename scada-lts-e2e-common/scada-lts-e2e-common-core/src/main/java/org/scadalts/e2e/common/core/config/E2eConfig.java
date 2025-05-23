package org.scadalts.e2e.common.core.config;

import org.scadalts.e2e.common.core.types.AuthType;
import org.scadalts.e2e.common.core.types.BrowserRef;
import org.scadalts.e2e.common.core.types.PageLoadStrategy;
import org.scadalts.e2e.common.core.types.TestPlan;

import java.util.Set;

public interface E2eConfig {
    PageLoadStrategy getPageLoadStrategy();

    BrowserRef getBrowserRef();

    AuthType getAuthType();

    TestPlan[] getTestPlans();

    java.net.URL getUrlAppBeingTested();

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

    String getUserSmtp();

    String getPasswordSmtp();

    String getHostSmtp();

    String getSendFrom();

    String getTitleEmail();

    String getTitleEmailSuccess();

    String getDataSourceName();

    String getDataPointName();

    String getDataPointSourceXid();

    String getDataPointTargetXid();

    String getRefreshSessionCron();

    String[] getClassesTestRefs();

    Set<SendTo> getSendTo();

    String[] getPointValuesToTests();

    String[] getBrowserOptionsArgs();

    String[] getBrowserOptionsPrefs();

    org.apache.logging.log4j.Level getLogLevel();

    int getTimeoutMs();

    int getPollingIntervalMs();

    int getAlarmListChangedAfterMs();

    int getAlarmListNoChangedAfterMs();

    int getWaitingAfterSetPointValueMs();

    int getPortProxy();

    int getPortApp();

    int getPortSmtp();

    long getDeleteEmailFromSentEmailsAfterMs();

    boolean isFastSetValueMode();

    boolean isProxyMode();

    boolean isHeadlessMode();

    boolean isScreenshotMode();

    @Deprecated
    boolean isDriverManagerMode();

    boolean isContinuousMode();

    boolean isDebugEmailMode();

    boolean isNotificationEmailMode();

    boolean isMailSmtpAuthMode();

    boolean isMailSmtpStarttlsMode();

    boolean isCheckAuthentication();

    boolean isUnblockSendEmailByCron();
    String getUnblockSendSuccessEmailCron();
    String getUnblockSendFailEmailCron();
    boolean isLoginDisabled();

    static E2eConfig defaultConfig() {
        return new E2eConfigDefault();
    }

    static E2eConfig config(Set<SendTo> sendTo, String emailTitle, String titleEmailSuccess,
                            String sendFrom, String hostSmtp) {
        E2eConfigDefault config = new E2eConfigDefault();
        config.setSendTo(sendTo);
        config.setTitleEmail(emailTitle);
        config.setTitleEmailSuccess(titleEmailSuccess);
        config.setSendFrom(sendFrom);
        config.setHostSmtp(hostSmtp);
        return config;
    }
}
