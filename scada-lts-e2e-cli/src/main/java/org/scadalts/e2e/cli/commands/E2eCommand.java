package org.scadalts.e2e.cli.commands;


import lombok.Getter;
import lombok.ToString;
import org.scadalts.e2e.cli.options.ScadaOptions;
import org.scadalts.e2e.cli.providers.VersionProvider;
import org.scadalts.e2e.common.api.E2eCommonApi;
import org.scadalts.e2e.common.impl.logo.AsciiHeadersImpl;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Spec;

@Getter
@ToString(callSuper = true)
@Command(name= "e2e",
        versionProvider = VersionProvider.class,
		resourceBundle = "lang.e2e", header = AsciiHeadersImpl.MAIN_HEADER,
		subcommands = {RunTestCommand.class, RunAppCommand.class,
				ConfigSystemCommand.class})
public class E2eCommand extends ScadaOptions implements Runnable {

	@Spec
	@ToString.Exclude
	private CommandLine.Model.CommandSpec spec;

	@Override
	public void run() {
		throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
	}

}