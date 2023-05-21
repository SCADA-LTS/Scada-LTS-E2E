package org.scadalts.e2e.cli.commands;

import lombok.Getter;
import org.scadalts.e2e.common.core.ansi.ConsoleColors;
import org.scadalts.e2e.cli.options.DefaultOptions;
import org.scadalts.e2e.common.core.utils.PropsPrintUtil;
import picocli.CommandLine;

@Getter
@CommandLine.Command(name = "config-system",
        resourceBundle = "lang.e2e")
public class ConfigSystemCommand extends DefaultOptions implements Runnable {

    @Override
    public void run() {
        String msg = PropsPrintUtil.msg(System.getProperties());
        PropsPrintUtil.print(ConsoleColors.GREEN + msg + ConsoleColors.GREEN);
    }
}
