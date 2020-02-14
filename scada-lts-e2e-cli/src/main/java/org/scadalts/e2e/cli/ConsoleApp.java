package org.scadalts.e2e.cli;

import org.scadalts.e2e.cli.commands.TestCommand;
import org.scadalts.e2e.common.utils.FileUtil;
import picocli.CommandLine;
import picocli.CommandLine.PropertiesDefaultProvider;

import java.io.File;

public class ConsoleApp {

    public static void main(String[] args) {

        File defaultsFile = FileUtil.getFile("config/scadalts-e2e-config.properties");
        PropertiesDefaultProvider propertiesDefaultProvider = new PropertiesDefaultProvider(defaultsFile);
        TestCommand test = new TestCommand();
        CommandLine testCmd = new CommandLine(test);
        testCmd.setDefaultValueProvider(propertiesDefaultProvider);
        testCmd.execute(args);
    }
}
