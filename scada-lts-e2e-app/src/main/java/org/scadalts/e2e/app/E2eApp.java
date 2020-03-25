package org.scadalts.e2e.app;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.config.Configurator;
import org.scadalts.e2e.app.infrastructure.config.ConfigAppKeys;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.config.E2eConfigFromFileProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.scadalts.e2e.common.utils.E2eSystemInfoUtil.printHeaderWithConfig;

@Log4j2
@SpringBootApplication
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
public class E2eApp {

	public static void main(String[] args) {
		run(new E2eConfigFromFileProvider().get(), args);
	}

	public static void run(E2eConfig config, String[] argsConsole) {

		Configurator.setRootLevel(config.getLogLevel());
		Configurator.setAllLevels("org.apache.logging.log4j", config.getLogLevel());
		printHeaderWithConfig(config);
		SpringApplication application = new SpringApplication(E2eApp.class);

		application.addInitializers(applicationEvent -> applicationEvent
				.getBeanFactory()
				.registerSingleton(E2eConfig.class.getName(), config));

		String port = MessageFormat.format(ConfigAppKeys.SERVER_PORT_KEY_X.getKey(), String.valueOf(config.getPortApp()));
		String cron = MessageFormat.format(ConfigAppKeys.CRON_PATTERN_KEY_X.getKey(), config.getCronPattern());
		String continuous = MessageFormat.format(ConfigAppKeys.CONTINUOUS_MODE_KEY_X.getKey(), config.isContinuousMode());
		String mail = MessageFormat.format(ConfigAppKeys.NOTIFICATION_EMAIL_MODE_KEY_X.getKey(), config.isNotificationEmailMode());
		String expires = MessageFormat.format(ConfigAppKeys.DELETE_EMAIL_FROM_SENT_EMAILS_AFTER_MS_KEY_X.getKey(), String.valueOf(config.getDeleteEmailFromSentEmailsAfterMs()));

		String[] args = _mergeArgs(argsConsole, port, cron, continuous, mail, expires);
		logger.debug("args: {} ", Arrays.asList(args));
		application.run(args);
        logger.info("-----------------------------------------------------4");
	}

	private static String[] _mergeArgs(String[] argsConsole, String... argsApp) {
		List<String> args = new ArrayList<>(Arrays.asList(argsConsole));
		args.addAll(Arrays.asList(argsApp));
		return args.toArray(new String[]{});
	}
}
