package org.scadalts.e2e.cli.commands;


import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.app.E2eApp;
import org.scadalts.e2e.cli.config.E2eConfigCli;
import org.scadalts.e2e.cli.options.AppOptions;
import org.scadalts.e2e.cli.providers.VersionProvider;
import org.scadalts.e2e.common.core.config.E2eConfig;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Log4j2
@Getter
@Command(name= "run-app",
        versionProvider = VersionProvider.class,
		resourceBundle = "lang.e2e")
public class RunAppCommand extends AppOptions implements Runnable {

	@ToString.Exclude
	@CommandLine.ParentCommand
	private E2eCommand e2eCommand;

	@Override
	public void run() {
		E2eConfig config = E2eConfigCli.builder()
				.fromRunApp(this)
				.fromE2e(e2eCommand)
				.build();
		E2eApp.run(config, new String[]{});
	}
}