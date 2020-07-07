package org.scadalts.e2e.cli.config;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.logging.log4j.Level;
import org.scadalts.e2e.cli.commands.E2eCommand;
import org.scadalts.e2e.cli.commands.RunAppCommand;
import org.scadalts.e2e.cli.commands.RunTestCommand;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.config.SendTo;
import org.scadalts.e2e.common.types.AuthType;
import org.scadalts.e2e.common.types.BrowserRef;
import org.scadalts.e2e.common.types.PageLoadStrategy;
import org.scadalts.e2e.common.types.TestPlan;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Set;

@ToString
@Builder
@EqualsAndHashCode
public class E2eConfigCli implements E2eConfig {

    private final RunTestCommand fromRunTest;
    private final RunAppCommand fromRunApp;
    private final E2eCommand fromE2e;

    @Override
    public BrowserRef getBrowserRef() {
        return fromE2e.getBrowserRef();
    }
    @Override
    public TestPlan getTestPlan() {
        return fromE2e.getTestPlan();
    }
    @Override
    public String getHostProxy() {
        return fromE2e.getHostProxy();
    }
    @Override
    public int getAlarmListChangedAfterMs() {
        return fromE2e.getAlarmListChangedAfterMs();
    }
    @Override
    public int getTimeoutMs() {
        return fromE2e.getTimeoutMs();
    }
    @Override
    public File getDriverFile() {
        return fromE2e.getDriverFile();
    }
    @Override
    public String[] getClassesTestRefs() {
        return fromE2e.getClassesTestRefs();
    }
    @Override
    public boolean isHeadlessMode() {
        return fromE2e.isHeadlessMode();
    }
    @Override
    public int getPollingIntervalMs() {
        return fromE2e.getPollingIntervalMs();
    }
    @Override
    public boolean isDriverManagerMode() {
        return fromE2e.isDriverManagerMode();
    }
    @Override
    public boolean isScreenshotMode() {
        return fromE2e.isScreenshotMode();
    }
    @Override
    public Path getReportsFolder() {
        return fromE2e.getReportsFolder();
    }
    @Override
    public boolean isFastSetValueMode() {
        return fromE2e.isFastSetValueMode();
    }
    @Override
    public boolean isProxyMode() {
        return fromE2e.isProxyMode();
    }
    @Override
    public PageLoadStrategy getPageLoadStrategy() {
        return fromE2e.getPageLoadStrategy();
    }
    @Override
    public int getAlarmListNoChangedAfterMs() {
        return fromE2e.getAlarmListNoChangedAfterMs();
    }
    @Override
    public String getGraphicalViewName() {
        return fromE2e.getGraphicalViewName();
    }
    @Override
    public int getPortProxy() {
        return fromE2e.getPortProxy();
    }
    @Override
    public URL getReportsUrl() {
        return fromE2e.getReportsUrl();
    }
    @Override
    public Level getLogLevel() {
        return fromE2e.getLogLevel();
    }
    @Override
    public URL getUrlAppBeingTested() {
        return fromE2e.getBaseUrl();
    }
    @Override
    public String getUserName() {
        return fromE2e.getUserName();
    }
    @Override
    public String getPassword() {
        return fromE2e.getPassword();
    }
    @Override
    public AuthType getAuthType() {
        return fromE2e.getAuthType();
    }

    @Override
    public String getDataPointToReadXid() {
        return fromE2e.getDataPointToReadXid();
    }

    @Override
    public String getDataPointToChangeXid() {
        return fromE2e.getDataPointToChangeXid();
    }

    @Override
    public int getWaitingAfterSetPointValueMs() {
        return fromE2e.getWaitingAfterSetPointValueMs();
    }

    @Override
    public int getPortApp() {
        return fromRunApp.getPortApp();
    }

    @Override
    public boolean isContinuousMode() {
        return fromRunApp.isContinuousMode();
    }

    @Override
    public String getCronPattern() {
        return fromRunApp.getCronPattern();
    }

    @Override
    public String getUserSmtp() {
        return fromRunApp.getUserSmtp();
    }

    @Override
    public String getPasswordSmtp() {
        return fromRunApp.getPasswordSmtp();
    }

    @Override
    public String getHostSmtp() {
        return fromRunApp.getHostSmtp();
    }

    @Override
    public int getPortSmtp() {
        return fromRunApp.getPortSmtp();
    }

    @Override
    public Set<SendTo> getSendTo() {
        return fromRunApp.getSendTo();
    }

    @Override
    public String getSendFrom() {
        return fromRunApp.getSendFrom();
    }

    @Override
    public boolean isDebugEmailMode() {
        return fromRunApp.isDebugEmailMode();
    }

    @Override
    public boolean isNotificationEmailMode() {
        return fromRunApp.isNotificationEmailMode();
    }

    @Override
    public long getDeleteEmailFromSentEmailsAfterMs() {
        return fromRunApp.getDeleteEmailFromSentEmailsAfterMs();
    }

    @Override
    public String getTitleEmail() {
        return fromRunApp.getTitleEmail();
    }

    @Override
    public String[] getPointValuesToTests() {
        return fromE2e.getPointValuesToTests();
    }

    @Override
    public String getDataSourceName() {
        return fromE2e.getDataSourceName();
    }

    @Override
    public String getDataPointName() {
        return fromE2e.getDataPointName();
    }

    @Override
    public String getDataPointTargetXid() {
        return fromE2e.getDataPointTargetXid();
    }

    @Override
    public String getDataPointSourceXid() {
        return fromE2e.getDataPointSourceXid();
    }

    @Override
    public String[] getBrowserOptionsArgs() {
        return fromE2e.getBrowserOptionsArgs();
    }

    @Override
    public String[] getBrowserOptionsPrefs() {
        return fromE2e.getBrowserOptionsPrefs();
    }

    @Override
    public boolean isMailSmtpStarttlsMode() {
        return fromRunApp.isMailSmtpStarttlsMode();
    }

    @Override
    public boolean isMailSmtpAuthMode() {
        return fromRunApp.isMailSmtpAuthMode();
    }
}
