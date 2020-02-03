package org.scadalts.e2e.test.api;

import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.page.api.E2ePageApi;
import org.scadalts.e2e.test.api.config.TestConfigurator;
import org.scadalts.e2e.test.core.plan.exec.TestsExecutable;
import org.scadalts.e2e.test.core.plan.provider.TestClassesProvider;
import org.scadalts.e2e.test.core.plan.runner.TestsRunnable;
import org.scadalts.e2e.test.impl.providers.ScadaTestClassByPlanProvider;
import org.scadalts.e2e.webservice.api.E2eWebServiceObjectApi;

public interface E2eTestApi {

    boolean run();

    static E2eTestApi newInstance(E2eConfig config) {

        E2ePageApi page = E2ePageApi.newInstance();
        page.init(config);

        E2eWebServiceObjectApi webService = E2eWebServiceObjectApi.newInstance("");
        webService.init(config);

        TestConfigurator.init(config);

        return new E2eTestApiImpl(TestsExecutable.newExecutor(TestClassesProvider.newInstance(new ScadaTestClassByPlanProvider()), TestsRunnable.newInstance()), config);
    }
}
