package org.scadalts.e2e.cli.commands;

import lombok.Getter;
import lombok.ToString;
import org.scadalts.e2e.cli.options.DefaultOptions;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model;
import picocli.CommandLine.Spec;

@Getter
@Command(name="test",
        subcommands = {E2eCommand.class})
public class TestCommand extends DefaultOptions implements Runnable {

    @Spec
    @ToString.Exclude
    private Model.CommandSpec spec;

    public void run() {
        throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
    }
}
