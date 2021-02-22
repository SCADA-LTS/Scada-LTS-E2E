package org.scadalts.e2e.common.core.config;

import org.scadalts.e2e.common.core.utils.FileUtil;

public class E2eConfigFromFileProvider implements E2eConfigProvider {

    private static final String RUN_APP_COMMAND = "test.e2e.run-app.";
    private static final String E2E_COMMAND = "test.e2e.";

    private static E2eConfig CONFIG = ConfigUtil.parse(
            FileUtil.getFileFromJar("config/scadalts-e2e-config.properties")
            .orElseThrow(IllegalStateException::new), RUN_APP_COMMAND, E2E_COMMAND);

    @Override
    public E2eConfig get() {
        return CONFIG;
    }
}
