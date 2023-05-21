package org.scadalts.e2e.cli.commands;

import lombok.Getter;
import lombok.ToString;
import org.scadalts.e2e.common.core.ansi.ConsoleColors;
import org.scadalts.e2e.cli.config.E2eConfigCli;
import org.scadalts.e2e.cli.options.DefaultOptions;
import org.scadalts.e2e.common.core.config.E2eConfig;
import org.scadalts.e2e.common.core.utils.PropsPrintUtil;
import picocli.CommandLine;

@Getter
@CommandLine.Command(name = "config-app",
        resourceBundle = "lang.e2e")
public class ConfigAppCommand extends DefaultOptions implements Runnable {

    @ToString.Exclude
    @CommandLine.ParentCommand
    private E2eCommand e2eCommand;

    @Override
    public void run() {
        E2eConfig config = E2eConfigCli.builder()
                .fromE2e(e2eCommand)
                .fromRunApp(new RunAppCommand())
                .fromRunTest(new RunTestCommand())
                .build();
        PropsPrintUtil.print(ConsoleColors.GREEN + config.toString() + ConsoleColors.GREEN);
    }
}
