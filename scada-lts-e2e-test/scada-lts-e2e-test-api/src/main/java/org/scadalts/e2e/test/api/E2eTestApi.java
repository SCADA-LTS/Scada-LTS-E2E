package org.scadalts.e2e.test.api;

import org.scadalts.e2e.common.core.config.E2eConfig;
import org.scadalts.e2e.test.core.plans.engine.E2eSummarable;
import org.scadalts.e2e.test.core.plans.engine.TestsRunEngine;
import org.scadalts.e2e.test.core.plans.exec.TestsExecutable;
import org.scadalts.e2e.test.core.plans.providers.TestClassesProvider;
import org.scadalts.e2e.test.impl.providers.ScadaTestClassByPlanProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

public interface E2eTestApi {

    E2eSummarable run(E2eConfig config);

   // void terminate(E2eConfig config);

    static E2eTestApi newInstance() {
        TestClassesProvider provider = TestClassesProvider.newInstance(new ScadaTestClassByPlanProvider());
        TestsExecutable executable = TestsExecutable.newExecutor(provider, TestsRunEngine.newInstance());
        return new E2eTestApiImpl(executable);
    }

    static void refreshPageSession() {
        TestWithPageUtil.logout();
        TestWithPageUtil.openNavigationPage();
    }

    static void refreshApiSession() {
        TestWithoutPageUtil.preparingTest();
        if(TestWithoutPageUtil.isApiLogged())
            TestWithoutPageUtil.logout();
    }
}
