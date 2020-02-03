package org.scadalts.e2e.app;


import lombok.Getter;
import lombok.ToString;
import org.apache.logging.log4j.Level;
import org.scadalts.e2e.app.options.DefaultOptions;
import org.scadalts.e2e.app.parsers.AuthTypeParser;
import org.scadalts.e2e.app.parsers.LogLevelParser;
import org.scadalts.e2e.app.providers.VersionProvider;
import org.scadalts.e2e.common.types.AuthType;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.net.URL;

@Getter
@ToString
@Command(name= "e2e",
        versionProvider = VersionProvider.class,
		resourceBundle = "lang.e2e")
public class E2e extends DefaultOptions implements Runnable {

	@Option(names = {"-l", "--log-level"}, converter = LogLevelParser.class)
	private Level logLevel;

	@Option(names = {"-U", "--url-app"})
	private URL urlApp;

	@Option(names = {"-u", "--user-name"})
	private String userName;

	@Option(names = {"-p", "--password"})
	private String password;

	@Option(names = {"-a", "--auth-type"}, converter = AuthTypeParser.class)
	private AuthType authType;

	@Spec
	private CommandLine.Model.CommandSpec spec;

	private boolean executed = false;

	@Override
	public void run() {
		this.executed = true;
		throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
	}

}