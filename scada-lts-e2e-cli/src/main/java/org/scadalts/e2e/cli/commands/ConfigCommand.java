package org.scadalts.e2e.cli.commands;

import lombok.Getter;
import lombok.ToString;
import picocli.CommandLine;

@Getter
@ToString
@CommandLine.Command(name = "config",
        resourceBundle = "lang.e2e")
public class ConfigCommand implements Runnable {

    @Override
    public void run() {

    }
}
