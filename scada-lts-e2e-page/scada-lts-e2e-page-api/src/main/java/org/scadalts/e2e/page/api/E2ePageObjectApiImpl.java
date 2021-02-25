package org.scadalts.e2e.page.api;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.config.E2eConfig;
import org.scadalts.e2e.page.core.config.PageObjectConfigurator;

@Log4j2
class E2ePageObjectApiImpl implements E2ePageObjectApi {

    @Override
    public void init(E2eConfig config) {
        PageObjectConfigurator.init(config);
    }

}
