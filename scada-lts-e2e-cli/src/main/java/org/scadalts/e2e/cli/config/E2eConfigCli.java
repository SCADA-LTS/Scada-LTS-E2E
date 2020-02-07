package org.scadalts.e2e.cli.config;

import lombok.Builder;
import lombok.ToString;
import org.apache.logging.log4j.Level;
import org.scadalts.e2e.cli.commands.E2eAppCommand;
import org.scadalts.e2e.cli.commands.E2eCommand;
import org.scadalts.e2e.cli.commands.RunTestCommand;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.types.AuthType;
import org.scadalts.e2e.common.types.BrowserRef;
import org.scadalts.e2e.common.types.PageLoadStrategy;
import org.scadalts.e2e.common.types.TestPlan;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

@ToString
@Builder
public class E2eConfigCli implements E2eConfig {

    private final RunTestCommand runTestCommand;
    private final E2eAppCommand e2eAppCommand;
    private final E2eCommand e2e;

    @Override
    public BrowserRef getBrowserRef() {
        return e2e.getBrowserRef();
    }
    @Override
    public int getCtrlCode() {
        return e2e.getCtrlCode();
    }
    @Override
    public TestPlan getTestPlan() {
        return e2e.getTestPlan();
    }
    @Override
    public String getHostProxy() {
        return e2e.getProxyHost();
    }
    @Override
    public int getAlarmListChangedAfterMs() {
        return e2e.getAlarmListChangedAfterMs();
    }
    @Override
    public int getTimeoutMs() {
        return e2e.getTimeoutMs();
    }
    @Override
    public File getDriverFile() {
        return e2e.getDriverFile();
    }
    @Override
    public String[] getClassesTestRefs() {
        return e2e.getClassesTestRefs();
    }
    @Override
    public boolean isHeadlessMode() {
        return e2e.isHeadlessMode();
    }
    @Override
    public int getPollingIntervalMs() {
        return e2e.getPollingIntervalMs();
    }
    @Override
    public boolean isDriverManagerMode() {
        return e2e.isDriverManagerMode();
    }
    @Override
    public boolean isScreenshotMode() {
        return e2e.isScreenshotMode();
    }
    @Override
    public Path getReportsFolder() {
        return e2e.getReportsFolder();
    }
    @Override
    public boolean isFastSetValueMode() {
        return e2e.isFastSetValueMode();
    }
    @Override
    public boolean isProxyMode() {
        return e2e.isProxyMode();
    }
    @Override
    public PageLoadStrategy getPageLoadStrategy() {
        return e2e.getPageLoadStrategy();
    }
    @Override
    public int getAlarmListNoChangedAfterMs() {
        return e2e.getAlarmListNoChangedAfterMs();
    }
    @Override
    public String getGraphicalViewName() {
        return e2e.getGraphicalViewName();
    }
    @Override
    public int getPortProxy() {
        return e2e.getProxyPort();
    }
    @Override
    public URL getReportsUrl() {
        return e2e.getReportsUrl();
    }
    @Override
    public Level getLogLevel() {
        return e2e.getLogLevel();
    }
    @Override
    public URL getBaseUrl() {
        return e2e.getBaseUrl();
    }
    @Override
    public String getUserName() {
        return e2e.getUserName();
    }
    @Override
    public String getPassword() {
        return e2e.getPassword();
    }
    @Override
    public AuthType getAuthType() {
        return e2e.getAuthType();
    }

    @Override
    public String getDataPointToReadXid() {
        return e2e.getDataPointToReadXid();
    }

    @Override
    public String getDataPointToChangeXid() {
        return e2e.getDataPointToChangeXid();
    }

    @Override
    public int getWaitingAfterSetPointValueMs() {
        return e2e.getWaitingAfterSetPointValueMs();
    }

    @Override
    public Level getLogLevelApp() {
        return e2eAppCommand.getLogLevelApp();
    }

    @Override
    public int getPortApp() {
        return e2eAppCommand.getPortApp();
    }

    @Override
    public boolean isSchedulingMode() {
        return e2eAppCommand.isSchedulingMode();
    }

    @Override
    public String getCronPattern() {
        return e2eAppCommand.getCronPattern();
    }
}
