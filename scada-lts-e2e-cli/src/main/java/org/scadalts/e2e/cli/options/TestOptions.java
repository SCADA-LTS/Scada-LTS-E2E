package org.scadalts.e2e.cli.options;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.scadalts.e2e.cli.parsers.BrowserRefParser;
import org.scadalts.e2e.cli.parsers.PageLoadStrategyParser;
import org.scadalts.e2e.cli.parsers.TestPlansParser;
import org.scadalts.e2e.common.core.types.BrowserRef;
import org.scadalts.e2e.common.core.types.PageLoadStrategy;
import org.scadalts.e2e.common.core.types.TestPlan;
import picocli.CommandLine;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

@Getter
@EqualsAndHashCode(callSuper = true)
public class TestOptions extends MainOptions {

    @CommandLine.Option(names = {"-b", "--browser-ref"}, converter = BrowserRefParser.class)
    private BrowserRef browserRef;

    @CommandLine.Option(names = {"-t", "--timeout-ms"})
    private int timeoutMs;

    @CommandLine.Option(names = {"-T", "--test-plans"}, required = true, converter = TestPlansParser.class, split = ";")
    private TestPlan[] testPlans;

    @CommandLine.Option(names = {"-d", "--driver-file"})
    private File driverFile;

    @CommandLine.Option(names = {"-H", "--headless-mode"}, negatable = true)
    private boolean headlessMode;

    @CommandLine.Option(names = {"-m", "--driver-manager-mode"}, negatable = true)
    private boolean driverManagerMode;

    @CommandLine.Option(names = {"-s", "--screenshot-mode"}, negatable = true)
    private boolean screenshotMode;

    @CommandLine.Option(names = {"-f", "--fast-set-value-mode"}, negatable = true)
    private boolean fastSetValueMode;

    @CommandLine.Option(names = {"-x", "--proxy-mode"}, negatable = true)
    private boolean proxyMode;

    @CommandLine.Option(names = {"-L", "--page-load-strategy"}, converter = PageLoadStrategyParser.class)
    private PageLoadStrategy pageLoadStrategy;

    @CommandLine.Option(names = {"-r", "--reports-url"})
    private URL reportsUrl;

    @CommandLine.Option(names = {"-R", "--reports-folder"})
    private Path reportsFolder;

    @CommandLine.Option(names = {"-i", "--polling-interval-ms"})
    private int pollingIntervalMs;

    @CommandLine.Option(names = {"-P", "--port-proxy"})
    private int portProxy;

    @CommandLine.Option(names = {"-X", "--host-proxy"})
    private String hostProxy;

    @CommandLine.Option(names = {"-n", "--classes-test-refs"}, split = ";", defaultValue = "")
    private String[] classesTestRefs;

    @CommandLine.Option(names = {"-o", "--browser-options-args"}, split = ";", defaultValue = "")
    private String[] browserOptionsArgs;

    @CommandLine.Option(names = {"-O", "--browser-options-prefs"}, split = ";", defaultValue = "")
    private String[] browserOptionsPrefs;

    @CommandLine.Option(names = {"-ca", "--check-authentication"}, negatable = true)
    private boolean checkAuthentication;

}
