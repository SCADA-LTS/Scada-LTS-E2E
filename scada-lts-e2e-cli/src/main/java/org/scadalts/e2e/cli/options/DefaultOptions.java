package org.scadalts.e2e.cli.options;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import picocli.CommandLine.Option;

@Getter
@EqualsAndHashCode
public class DefaultOptions {

    @Option(names = {"-v", "--version"}, versionHelp = true)
    private boolean version;

    @Option(names = {"-h", "--help", "-?", "-help"}, usageHelp = true)
    private boolean help;
}
