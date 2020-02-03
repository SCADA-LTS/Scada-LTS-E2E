package org.scadalts.e2e.page.api;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.page.core.config.PageConfigurator;

@Log4j2
class E2ePageApiImpl implements E2ePageApi {

    @Override
    public void init(E2eConfig config) {
        PageConfigurator.init(config);
    }

}
