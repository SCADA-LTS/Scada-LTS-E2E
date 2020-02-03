package org.scadalts.e2e.app;

import lombok.Getter;
import picocli.CommandLine;
import picocli.CommandLine.*;

@Getter
@Command(name="test")
public class Test implements Runnable {

    @Spec
    private Model.CommandSpec spec;

    private boolean executed = false;

    public void run() {
        executed = true;
        throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
    }
}
