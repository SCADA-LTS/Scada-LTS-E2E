package org.scadalts.e2e.app;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.config.Configurator;
import org.scadalts.e2e.app.config.ConfigAppKeys;
import org.scadalts.e2e.common.config.E2eConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.MessageFormat;

@Log4j2
@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
public class E2eApp {

	public static void main(String[] args) {
	}

	public static void run(E2eConfig config) {

		Configurator.setRootLevel(config.getLogLevelApp());
		Configurator.setAllLevels("org.apache.logging.log4j", config.getLogLevelApp());

		logger.info("-----------------------------------------------------1");
		logger.info(config);
		logger.info("-----------------------------------------------------2");
		SpringApplication springApplication = new SpringApplication(E2eApp.class);

		springApplication.addInitializers(applicationEvent -> applicationEvent
				.getBeanFactory()
				.registerSingleton(E2eConfig.class.getName(), config));

		String portOpts = MessageFormat.format(ConfigAppKeys.SERVER_PORT_KEY_X.getKey(), String.valueOf(config.getPortApp()));
		String cron = MessageFormat.format(ConfigAppKeys.CRON_PATTERN_KEY_X.getKey(), config.getCronPattern());
		String scheduling = MessageFormat.format(ConfigAppKeys.SCHEDULING_ENABLED_KEY_X.getKey(), config.isSchedulingMode());

		springApplication.run(portOpts, cron, scheduling);
        logger.info("-----------------------------------------------------3");
	}
}
