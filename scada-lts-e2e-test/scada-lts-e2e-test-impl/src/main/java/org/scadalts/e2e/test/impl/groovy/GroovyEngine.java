package org.scadalts.e2e.test.impl.groovy;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.common.core.config.E2eConfiguration;
import org.scadalts.e2e.page.core.config.PageConfiguration;
import org.scadalts.e2e.page.impl.groovy.*;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.Collection;

import static org.scadalts.e2e.test.core.config.TestCoreConfigurator.isLoginEnabled;
import static org.scadalts.e2e.test.impl.groovy.CreatorUtil.deleteObjects;
import static org.scadalts.e2e.test.impl.groovy.GroovyUtil.getGroovyExecutes;

@Log4j2
@RunWith(Parameterized.class)
public class GroovyEngine {

    @Parameterized.Parameters(name = "number test: {index}, script: {0}")
    public static Collection<GroovyExecute> data() {
        return getGroovyExecutes();
    }

    private final GroovyExecute execute;

    public GroovyEngine(GroovyExecute execute) {
        this.execute = execute;
    }

    @Before
    public void config() {
        _preconfig(execute);
        _config(execute);
    }

    @Test
    public void execute() {
        _test(execute);
    }

    @After
    public void after() {
        if(ConfigurationUtil.isPageMode()) {
            if (TestWithPageUtil.isLogged())
                TestWithPageUtil.close();
        } else {
            if(isLoginEnabled()
                    && (!E2eConfiguration.checkAuthentication || TestWithoutPageUtil.isApiLogged()))
                TestWithoutPageUtil.close();
        }
        ConfigurationUtil.pageMode(false);
    }

    @AfterClass
    public static void clean() {
        try {
            _clean();
        } finally {
            System.gc();
        }
    }

    private static void _clean() {
        deleteObjects();
        ConfigurationUtil.headless(PageConfiguration.headless);
        ConfigurationUtil.path(PageConfiguration.reportsUrl);
        ConfigurationUtil.baseUrl(PageConfiguration.baseUrl);
        if(ConfigurationUtil.isPageMode()) {
            if (isLoginEnabled() && TestWithPageUtil.isLogged())
                TestWithPageUtil.close();
        } else {
            if(isLoginEnabled()
                    && (!E2eConfiguration.checkAuthentication || TestWithoutPageUtil.isApiLogged()))
                TestWithoutPageUtil.close();
        }
    }

    private void _preconfig(GroovyExecute execute) {
        execute.getGroovyObject().invokeMethod("preconfig", new Object[0]);
        if(ConfigurationUtil.isPageMode()) {
            NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();
            NavigationUtil.init(navigationPage);
            OperationUtil.init(navigationPage);
            OperationPageUtil.init(navigationPage);
            CreatorUtil.init(navigationPage);
            EditorUtil.init(navigationPage);
        } else {
            TestWithoutPageUtil.preparingTest();
        }
    }

    private void _config(GroovyExecute execute) {
        execute.getGroovyObject().invokeMethod("config", new Object[0]);
    }

    private void _test(GroovyExecute execute) {
        if(!execute.getData().isEmpty()) {
            execute.getGroovyObject().invokeMethod("test", execute.getData());
        } else {
            execute.getGroovyObject().invokeMethod("test", new Object[0]);
        }
    }

}
