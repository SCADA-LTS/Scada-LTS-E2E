package org.scadalts.e2e.app.commands;

import lombok.Getter;
import lombok.ToString;
import org.scadalts.e2e.app.options.DefaultOptions;
import org.scadalts.e2e.app.parsers.BrowserRefParser;
import org.scadalts.e2e.app.parsers.PageLoadStrategyParser;
import org.scadalts.e2e.app.parsers.TestPlansParser;
import org.scadalts.e2e.app.providers.VersionProvider;
import org.scadalts.e2e.common.types.BrowserRef;
import org.scadalts.e2e.common.types.PageLoadStrategy;
import org.scadalts.e2e.common.types.TestPlan;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

@Getter
@ToString
@Command(name = "run-test",
        versionProvider = VersionProvider.class,
        resourceBundle = "lang.e2e", header = {
                "__  ___/___________ ______  /_____ _      ___  ____/_|__ \\__  ____/\n",
                "_____ \\_  ___/  __ `/  __  /_  __ `/________  __/  ____/ /_  __/   \n",
                "____/ // /__ / /_/ // /_/ / / /_/ /_/_____/  /___  _  __/_  /___   \n",
                "/____/ \\___/ \\__,_/ \\__,_/  \\__,_/        /_____/  /____//_____/"})
public class RunTestCommand extends DefaultOptions implements Runnable {

    @Option(names = {"-b", "--browser-ref"}, converter = BrowserRefParser.class)
    private BrowserRef browserRef;

    @Option(names = {"-t", "--timeout-ms"})
    private int timeoutMs;

    @Option(names = {"-d", "--driver-file"})
    private File driverFile;

    @Option(names = {"-k", "--ctrl-code"})
    private int ctrlCode;

    @Option(names = {"-H", "--headless-mode-enabled"})
    private boolean headless;

    @Option(names = {"-m", "--driver-manager-enabled-sel"})
    private boolean driverManagerEnabled;

    @Option(names = {"-s", "--screenshot-enabled-sel"})
    private boolean screenshotEnabled;

    @Option(names = {"-f", "--fast-set-value-enabled-sel"})
    private boolean fastSetValueEnabled;

    @Option(names = {"-x", "--proxy-enabled-sel"})
    private boolean proxyEnabled;

    @Option(names = {"-L", "--page-load-strategy-sel"}, converter = PageLoadStrategyParser.class)
    private PageLoadStrategy pageLoadStrategy;

    @Option(names = {"-r", "--reports-url-sel"})
    private URL reportsUrl;

    @Option(names = {"-R", "--reports-folder-sel"})
    private Path reportsFolder;

    @Option(names = {"-i", "--polling-interval-ms-sel"})
    private int pollingIntervalMs;

    @Option(names = {"-P", "--proxy-port-sel"})
    private int proxyPort;

    @Option(names = {"-X", "--proxy-host-sel"})
    private String proxyHost;

    @Option(names = {"-n", "--classes-test-refs"}, split = ";", defaultValue = "")
    private String[] classesTestRefs;

    @Option(names = {"-c", "--alarm-list-changed-after-ms-sca"})
    private int alarmListChangedAfterMs;

    @Option(names = {"-C", "--alarm-list-no-changed-after-ms-sca"})
    private int alarmListNoChangedAfterMs;

    @Option(names = {"-w", "--waiting-after-set-point-value-ms-sca"})
    private int waitingAfterSetPointValueMs;

    @Option(names = {"-g", "--graphical-view-name-sca"})
    private String graphicalViewName;

    @Option(names = {"-T", "--test-plan"}, required = true, converter = TestPlansParser.class)
    private TestPlan testPlan;

    @Option(names = {"-e", "--data-point-to-change-xid-sca"})
    private String dataPointToChangeXid;

    @Option(names = {"-E", "--data-point-to-read-xid-sca"})
    private String dataPointToReadXid;

    @Override
    public void run() {
    }
}
