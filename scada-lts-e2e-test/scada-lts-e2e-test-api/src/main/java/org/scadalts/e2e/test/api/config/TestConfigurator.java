package org.scadalts.e2e.test.api.config;

import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.page.core.config.PageObjectConfigurator;
import org.scadalts.e2e.service.core.config.ServiceObjectConfigurator;
import org.scadalts.e2e.test.core.config.TestCoreConfigurator;
import org.scadalts.e2e.test.impl.config.TestImplConfigurator;

public class TestConfigurator {

    public static void init(E2eConfig config) {
        if(config == null) {
            return;
        }
        TestCoreConfigurator.init(config);
        TestImplConfigurator.init(config);
        PageObjectConfigurator.init(config);
        ServiceObjectConfigurator.init(config);
    }
}
