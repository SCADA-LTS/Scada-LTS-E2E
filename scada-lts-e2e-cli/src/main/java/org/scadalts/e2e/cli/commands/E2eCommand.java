package org.scadalts.e2e.cli.commands;


import lombok.Getter;
import lombok.ToString;
import org.apache.logging.log4j.Level;
import org.scadalts.e2e.cli.config.AsciiHeaders;
import org.scadalts.e2e.cli.options.TestOptions;
import org.scadalts.e2e.cli.parsers.AuthTypeParser;
import org.scadalts.e2e.cli.parsers.LogLevelParser;
import org.scadalts.e2e.cli.providers.VersionProvider;
import org.scadalts.e2e.common.types.AuthType;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;

import java.net.URL;

@Getter
@ToString(callSuper = true)
@Command(name= "e2e",
        versionProvider = VersionProvider.class,
		resourceBundle = "lang.e2e", header = AsciiHeaders.MAIN_HEADER,
		subcommands = {RunTestCommand.class, E2eAppCommand.class, ConfigSystemCommand.class})
public class E2eCommand extends TestOptions implements Runnable {

	@Option(names = {"-l", "--log-level"}, converter = LogLevelParser.class)
	private Level logLevel;

	@Option(names = {"-U", "--base-url"})
	private URL baseUrl;

	@Option(names = {"-u", "--user-name"})
	private String userName;

	@Option(names = {"-p", "--password"})
	private String password;

	@Option(names = {"-a", "--auth-type"}, converter = AuthTypeParser.class)
	private AuthType authType;

	@Spec
	private CommandLine.Model.CommandSpec spec;

	@Override
	public void run() {
		throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
	}

}