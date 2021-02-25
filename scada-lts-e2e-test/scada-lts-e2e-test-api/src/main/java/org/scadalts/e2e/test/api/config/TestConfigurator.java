package org.scadalts.e2e.test.api.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.config.ConfigHandler;
import org.scadalts.e2e.common.core.config.E2eConfig;
import org.scadalts.e2e.page.core.config.PageObjectConfigurator;
import org.scadalts.e2e.service.core.config.ServiceObjectConfigurator;
import org.scadalts.e2e.test.core.config.TestCoreConfigurator;
import org.scadalts.e2e.test.impl.config.TestImplConfigurator;

import java.util.Objects;

@Log4j2
public class TestConfigurator {

    private static E2eConfig CONFIG;

    private TestConfigurator() {
    }

    public static void init(E2eConfig config) {
        if(!Objects.isNull(CONFIG)) {
            return;
        }
        if(Objects.isNull(config)) {
            logger.warn("Config is null");
            return;
        }
        CONFIG = ConfigHandler.handle(config);
        TestCoreConfigurator.init(config);
        TestImplConfigurator.init(config);
        PageObjectConfigurator.init(config);
        ServiceObjectConfigurator.init(config);
    }
}
