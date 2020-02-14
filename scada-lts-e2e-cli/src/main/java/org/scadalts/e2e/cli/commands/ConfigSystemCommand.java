package org.scadalts.e2e.cli.commands;

import lombok.Getter;
import lombok.ToString;
import org.scadalts.e2e.common.ansi.ConsoleColors;
import org.scadalts.e2e.cli.options.DefaultOptions;
import org.scadalts.e2e.common.utils.PropsPrintUtil;
import picocli.CommandLine;

@Getter
@ToString
@CommandLine.Command(name = "config-system",
        resourceBundle = "lang.e2e")
public class ConfigSystemCommand extends DefaultOptions implements Runnable {

    @Override
    public void run() {
        String msg = PropsPrintUtil.msg(System.getProperties());
        PropsPrintUtil.print(ConsoleColors.GREEN + msg + ConsoleColors.GREEN);
    }
}
