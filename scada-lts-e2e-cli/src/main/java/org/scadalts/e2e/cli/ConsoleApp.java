package org.scadalts.e2e.cli;

import org.scadalts.e2e.cli.commands.TestCommand;
import org.scadalts.e2e.common.core.utils.FileUtil;
import picocli.CommandLine;
import picocli.CommandLine.PropertiesDefaultProvider;

import java.io.File;

public class ConsoleApp {

    public static void main(String[] args) {

        File defaultsFile = FileUtil.getFileFromJar("config/scadalts-e2e-config.properties").orElseThrow(IllegalStateException::new);
        PropertiesDefaultProvider propertiesDefaultProvider = new PropertiesDefaultProvider(defaultsFile);
        TestCommand test = new TestCommand();
        CommandLine testCmd = new CommandLine(test);
        testCmd.setDefaultValueProvider(propertiesDefaultProvider);
        testCmd.execute(args);
    }
}
