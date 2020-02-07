package org.scadalts.e2e.cli.commands;


import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.scadalts.e2e.app.E2eApp;
import org.scadalts.e2e.cli.config.E2eConfigCli;
import org.scadalts.e2e.cli.options.DefaultOptions;
import org.scadalts.e2e.cli.parsers.LogLevelParser;
import org.scadalts.e2e.cli.providers.VersionProvider;
import org.scadalts.e2e.common.config.E2eConfig;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Log4j2
@Getter
@ToString
@Command(name= "run-app",
        versionProvider = VersionProvider.class,
		resourceBundle = "lang.e2e")
public class E2eAppCommand extends DefaultOptions implements Runnable {

	@ToString.Exclude
	@CommandLine.ParentCommand
	private E2eCommand e2eCommand;

	@CommandLine.Option(names = {"-l", "--log-level-spr"}, converter = LogLevelParser.class)
	private Level logLevelApp;

	@CommandLine.Option(names = {"-P", "--port-app-spr"})
	private int portApp;

	@CommandLine.Option(names = {"-q", "--cron-pattern-spr"})
	private String cronPattern;

	@CommandLine.Option(names = {"-Q", "--scheduling-mode-spr"}, negatable = true)
	private boolean schedulingMode;

	@Override
	public void run() {
		E2eConfig config = E2eConfigCli.builder()
				.e2eAppCommand(this)
				.e2e(e2eCommand)
				.build();
		E2eApp.run(config);
	}
}