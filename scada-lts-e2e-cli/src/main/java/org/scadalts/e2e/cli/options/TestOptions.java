package org.scadalts.e2e.cli.options;

import lombok.Getter;
import lombok.ToString;
import org.scadalts.e2e.cli.parsers.BrowserRefParser;
import org.scadalts.e2e.cli.parsers.PageLoadStrategyParser;
import org.scadalts.e2e.cli.parsers.TestPlansParser;
import org.scadalts.e2e.common.types.BrowserRef;
import org.scadalts.e2e.common.types.PageLoadStrategy;
import org.scadalts.e2e.common.types.TestPlan;
import picocli.CommandLine;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

@Getter
@ToString
public class TestOptions extends DefaultOptions {

    @CommandLine.Option(names = {"-b", "--browser-ref"}, converter = BrowserRefParser.class)
    private BrowserRef browserRef;

    @CommandLine.Option(names = {"-t", "--timeout-ms"})
    private int timeoutMs;

    @CommandLine.Option(names = {"-d", "--driver-file"})
    private File driverFile;

    @CommandLine.Option(names = {"-k", "--ctrl-code"})
    private int ctrlCode;

    @CommandLine.Option(names = {"-H", "--headless-mode"}, negatable = true)
    private boolean headlessMode;

    @CommandLine.Option(names = {"-m", "--driver-manager-mode-sel"}, negatable = true)
    private boolean driverManagerMode;

    @CommandLine.Option(names = {"-s", "--screenshot-mode-sel"}, negatable = true)
    private boolean screenshotMode;

    @CommandLine.Option(names = {"-f", "--fast-set-value-mode-sel"}, negatable = true)
    private boolean fastSetValueMode;

    @CommandLine.Option(names = {"-x", "--proxy-mode-sel"}, negatable = true)
    private boolean proxyMode;

    @CommandLine.Option(names = {"-L", "--page-load-strategy-sel"}, converter = PageLoadStrategyParser.class)
    private PageLoadStrategy pageLoadStrategy;

    @CommandLine.Option(names = {"-r", "--reports-url-sel"})
    private URL reportsUrl;

    @CommandLine.Option(names = {"-R", "--reports-folder-sel"})
    private Path reportsFolder;

    @CommandLine.Option(names = {"-i", "--polling-interval-ms-sel"})
    private int pollingIntervalMs;

    @CommandLine.Option(names = {"-P", "--port-proxy-sel"})
    private int proxyPort;

    @CommandLine.Option(names = {"-X", "--host-proxy-sel"})
    private String proxyHost;

    @CommandLine.Option(names = {"-n", "--classes-test-refs"}, split = ";", defaultValue = "")
    private String[] classesTestRefs;

    @CommandLine.Option(names = {"-c", "--alarm-list-changed-after-ms-sca"})
    private int alarmListChangedAfterMs;

    @CommandLine.Option(names = {"-C", "--alarm-list-no-changed-after-ms-sca"})
    private int alarmListNoChangedAfterMs;

    @CommandLine.Option(names = {"-w", "--waiting-after-set-point-value-ms-sca"})
    private int waitingAfterSetPointValueMs;

    @CommandLine.Option(names = {"-g", "--graphical-view-name-sca"})
    private String graphicalViewName;

    @CommandLine.Option(names = {"-T", "--test-plan"}, required = true, converter = TestPlansParser.class)
    private TestPlan testPlan;

    @CommandLine.Option(names = {"-e", "--data-point-to-change-xid-sca"})
    private String dataPointToChangeXid;

    @CommandLine.Option(names = {"-E", "--data-point-to-read-xid-sca"})
    private String dataPointToReadXid;
}