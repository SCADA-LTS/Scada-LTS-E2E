package org.scadalts.e2e.test.impl.groovy;

import lombok.extern.log4j.Log4j2;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.common.core.config.E2eConfigurator;
import org.scadalts.e2e.page.core.config.PageConfiguration;
import org.scadalts.e2e.page.impl.groovy.*;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.*;

import static org.scadalts.e2e.common.core.utils.ResourcesUtil.getFilesFromJar;
import static org.scadalts.e2e.test.impl.groovy.CreatorUtil.deleteObjects;
import static org.scadalts.e2e.test.impl.groovy.GroovyUtil.getGroovyExecutes;

@Log4j2
@RunWith(Parameterized.class)
public class GroovyEngine {

    @Parameterized.Parameters(name = "number test: {index}, groovy script: {0}")
    public static Collection<GroovyExecute> data() {
        E2eConfigurator.configGroovy();
        getFilesFromJar("groovy", GroovyEngine.class);
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

    @AfterClass
    public static void clean() {
        _clean();
    }

    private static void _clean() {
        deleteObjects();
        ConfigurationUtil.headless(PageConfiguration.headless);
        ConfigurationUtil.path(PageConfiguration.reportsUrl);
        if (TestWithPageUtil.isLogged())
            TestWithPageUtil.close();
    }

    private void _preconfig(GroovyExecute execute) {
        if (TestWithPageUtil.isLogged())
            TestWithPageUtil.close();
        execute.getGroovyObject().invokeMethod("preconfig", new Object[0]);
        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();
        NavigationUtil.init(navigationPage);
        OperationUtil.init(navigationPage);
        OperationPageUtil.init(navigationPage);
        CreatorUtil.init(navigationPage);
        EditorUtil.init(navigationPage);
    }

    private void _config(GroovyExecute execute) {
        execute.getGroovyObject().invokeMethod("config", new Object[0]);
    }

    private void _test(GroovyExecute execute) {
        execute.getGroovyObject().invokeMethod("test", new Object[0]);
    }

}
