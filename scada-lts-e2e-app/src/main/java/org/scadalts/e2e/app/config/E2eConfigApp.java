package org.scadalts.e2e.app.config;

import lombok.ToString;
import org.apache.logging.log4j.Level;
import org.scadalts.e2e.app.E2e;
import org.scadalts.e2e.app.commands.RunTestCommand;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.types.AuthType;
import org.scadalts.e2e.common.types.BrowserRef;
import org.scadalts.e2e.common.types.PageLoadStrategy;
import org.scadalts.e2e.common.types.TestPlan;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

@ToString
public class E2eConfigApp implements E2eConfig {

    private final RunTestCommand runTestCommand;
    private final E2e e2e;

    public E2eConfigApp(RunTestCommand runTestCommand, E2e e2e) {
        this.runTestCommand = runTestCommand;
        this.e2e = e2e;
    }
    @Override
    public BrowserRef getBrowserRef() {
        return runTestCommand.getBrowserRef();
    }
    @Override
    public int getCtrlCode() {
        return runTestCommand.getCtrlCode();
    }
    @Override
    public TestPlan getTestPlan() {
        return runTestCommand.getTestPlan();
    }
    @Override
    public String getProxyHost() {
        return runTestCommand.getProxyHost();
    }
    @Override
    public int getAlarmListChangedAfterMs() {
        return runTestCommand.getAlarmListChangedAfterMs();
    }
    @Override
    public int getTimeoutMs() {
        return runTestCommand.getTimeoutMs();
    }
    @Override
    public File getDriverFile() {
        return runTestCommand.getDriverFile();
    }
    @Override
    public String[] getClassesTestRefs() {
        return runTestCommand.getClassesTestRefs();
    }
    @Override
    public boolean isHeadlessModeEnabled() {
        return runTestCommand.isHeadless();
    }
    @Override
    public int getPollingIntervalMs() {
        return runTestCommand.getPollingIntervalMs();
    }
    @Override
    public boolean isDriverManagerEnabled() {
        return runTestCommand.isDriverManagerEnabled();
    }
    @Override
    public boolean isScreenshotEnabled() {
        return runTestCommand.isScreenshotEnabled();
    }
    @Override
    public Path getReportsFolder() {
        return runTestCommand.getReportsFolder();
    }
    @Override
    public boolean isFastSetValueEnabled() {
        return runTestCommand.isFastSetValueEnabled();
    }
    @Override
    public boolean isProxyEnabled() {
        return runTestCommand.isProxyEnabled();
    }
    @Override
    public PageLoadStrategy getPageLoadStrategy() {
        return runTestCommand.getPageLoadStrategy();
    }
    @Override
    public int getAlarmListNoChangedAfterMs() {
        return runTestCommand.getAlarmListNoChangedAfterMs();
    }
    @Override
    public String getGraphicalViewName() {
        return runTestCommand.getGraphicalViewName();
    }
    @Override
    public int getProxyPort() {
        return runTestCommand.getProxyPort();
    }
    @Override
    public URL getReportsUrl() {
        return runTestCommand.getReportsUrl();
    }
    @Override
    public Level getLogLevel() {
        return e2e.getLogLevel();
    }
    @Override
    public URL getBaseUrl() {
        return e2e.getUrlApp();
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
        return runTestCommand.getDataPointToReadXid();
    }

    @Override
    public String getDataPointToChangeXid() {
        return runTestCommand.getDataPointToChangeXid();
    }
}
