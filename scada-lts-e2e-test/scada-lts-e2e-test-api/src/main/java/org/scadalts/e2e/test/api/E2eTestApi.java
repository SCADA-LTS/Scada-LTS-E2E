package org.scadalts.e2e.test.api;

import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.config.E2eConfigurator;
import org.scadalts.e2e.page.api.E2ePageObjectApi;
import org.scadalts.e2e.test.api.config.TestConfigurator;
import org.scadalts.e2e.test.core.plan.exec.TestsExecutable;
import org.scadalts.e2e.test.core.plan.provider.TestClassesProvider;
import org.scadalts.e2e.test.core.plan.runner.TestsRunnable;
import org.scadalts.e2e.test.impl.providers.ScadaTestClassByPlanProvider;
import org.scadalts.e2e.webservice.api.E2eWebServiceObjectApi;

public interface E2eTestApi {

    boolean run();

    static E2eTestApi newInstance(E2eConfig config) {

        E2eConfigurator.init(config);
        TestConfigurator.init(config);

        E2ePageObjectApi page = E2ePageObjectApi.newInstance();
        page.init(config);

        E2eWebServiceObjectApi webService = E2eWebServiceObjectApi.newInstance("");
        webService.init(config);

        TestClassesProvider provider = TestClassesProvider.newInstance(new ScadaTestClassByPlanProvider());
        TestsExecutable executable = TestsExecutable.newExecutor(provider, TestsRunnable.newInstance());

        return new E2eTestApiImpl(executable, config);
    }
}
