package org.scadalts.e2e.app;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.config.Configurator;
import org.scadalts.e2e.app.infrastructure.config.ConfigAppKeys;
import org.scadalts.e2e.common.api.E2eCommonApi;
import org.scadalts.e2e.common.core.config.E2eConfig;
import org.scadalts.e2e.common.core.config.E2eConfigFromFileProvider;
import org.scadalts.e2e.common.core.utils.E2eSystemInfoUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		E2eSystemInfoUtil systemInfo = new E2eSystemInfoUtil(E2eCommonApi.createAsciiHeaders());
		systemInfo.printHeaderWithConfig(config);
		SpringApplication application = new SpringApplication(E2eApp.class);

		application.addInitializers(applicationEvent -> applicationEvent
				.getBeanFactory()
				.registerSingleton(E2eConfig.class.getName(), config));

		String port = MessageFormat.format(ConfigAppKeys.SERVER_PORT_KEY_X.getKey(), String.valueOf(config.getPortApp()));
		String cron = MessageFormat.format(ConfigAppKeys.CRON_PATTERN_KEY_X.getKey(), config.getCronPattern());
		String continuous = MessageFormat.format(ConfigAppKeys.CONTINUOUS_MODE_KEY_X.getKey(), config.isContinuousMode());
		String mail = MessageFormat.format(ConfigAppKeys.NOTIFICATION_EMAIL_MODE_KEY_X.getKey(), config.isNotificationEmailMode());
		String expires = MessageFormat.format(ConfigAppKeys.DELETE_EMAIL_FROM_SENT_EMAILS_AFTER_MS_KEY_X.getKey(), String.valueOf(config.getDeleteEmailFromSentEmailsAfterMs()));
		String refreshSessionCron =  MessageFormat.format(ConfigAppKeys.REFRESH_SESSION_CRON_KEY_X.getKey(), config.getRefreshSessionCron());
		String unblockSendFailEmailCron = MessageFormat.format(ConfigAppKeys.UNBLOCK_SEND_FAIL_EMAIL_CROM.getKey(), config.getUnblockSendFailEmailCron());
		String unblockSendSuccessEmailCron = MessageFormat.format(ConfigAppKeys.UNBLOCK_SEND_SUCCESS_EMAIL_CROM.getKey(), config.getUnblockSendSuccessEmailCron());
		String unblockSendEmailByCron = MessageFormat.format(ConfigAppKeys.UNBLOCK_SEND_EMAIL_BY_CROM.getKey(), config.isUnblockSendEmailByCron());

		String[] args = _mergeArgs(argsConsole, port, cron, continuous, mail, expires, refreshSessionCron, unblockSendFailEmailCron, unblockSendSuccessEmailCron, unblockSendEmailByCron);
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
