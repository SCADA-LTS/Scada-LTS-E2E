package org.scadalts.e2e.cli.options;

import lombok.Getter;
import picocli.CommandLine.Option;

@Getter
public class DefaultOptions {

    @Option(names = {"-v", "--version"}, versionHelp = true)
    private boolean version;

    @Option(names = {"-h", "--help"}, usageHelp = true)
    private boolean help;
}
