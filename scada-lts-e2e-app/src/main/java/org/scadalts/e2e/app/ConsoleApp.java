package org.scadalts.e2e.app;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.config.Configurator;
import org.scadalts.e2e.app.commands.ConfigSystemCommand;
import org.scadalts.e2e.app.commands.RunTestCommand;
import org.scadalts.e2e.app.config.E2eConfigApp;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.util.FileUtils;
import org.scadalts.e2e.test.api.E2eTestApi;
import picocli.CommandLine;
import picocli.CommandLine.PropertiesDefaultProvider;

import java.io.File;

@Log4j2
public class ConsoleApp {

    public static void main(String[] args) {

        RunTestCommand runTestCommand = new RunTestCommand();
        ConfigSystemCommand configSystemCommand = new ConfigSystemCommand();

        E2e e2e = new E2e();
        CommandLine cmdE2e = new CommandLine(e2e);
        cmdE2e.addSubcommand(runTestCommand);
        cmdE2e.addSubcommand(configSystemCommand);

        Test test = new Test();
        CommandLine mainCmd = new CommandLine(test);
        mainCmd.addSubcommand(cmdE2e);

        File defaultsFile = FileUtils.createNewFileInFileSystem("config/scadalts-e2e-config.properties");
        PropertiesDefaultProvider propertiesDefaultProvider = new PropertiesDefaultProvider(defaultsFile);

        mainCmd.setDefaultValueProvider(propertiesDefaultProvider);
        mainCmd.execute(args);

        Configurator.setRootLevel(e2e.getLogLevel());
        Configurator.setAllLevels("org.apache.logging.log4j", e2e.getLogLevel());

        if(runTestCommand.isHelp() || runTestCommand.isVersion() || e2e.isHelp()
                || e2e.isVersion() || configSystemCommand.isExecuted() || e2e.isExecuted()) {
            return;
        }

        E2eConfig config = new E2eConfigApp(runTestCommand, e2e);
        E2eTestApi tests = E2eTestApi.newInstance(config);
        logger.info("success:{}",tests.run());
    }
}
