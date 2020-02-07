package org.scadalts.e2e.cli.commands;

import lombok.Getter;
import lombok.ToString;
import org.scadalts.e2e.common.util.PropsPrintUtil;
import picocli.CommandLine;

import java.util.ArrayList;

@Getter
@ToString
@CommandLine.Command(name = "config-system",
        resourceBundle = "lang.e2e")
public class ConfigSystemCommand implements Runnable {

    @Override
    public void run() {
        CommandLine.Help.Ansi ansi = CommandLine.Help.Ansi.ON;

        String msg = PropsPrintUtil.msg(System.getProperties());
        ansi.apply("apply: "+msg, new ArrayList<>());

        String msgPretty = ansi.string("string: "+msg);
        PropsPrintUtil.print("print pretty: "+msgPretty);
    }
}
