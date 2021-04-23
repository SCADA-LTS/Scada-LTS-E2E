package org.scadalts.e2e.test.impl.groovy;

import groovy.lang.*;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.common.core.config.E2eConfigurator;
import org.scadalts.e2e.page.core.config.PageConfiguration;
import org.scadalts.e2e.page.impl.groovy.*;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

import static org.scadalts.e2e.test.impl.groovy.CreatorUtil.deleteObjects;

@Log4j2
@RunWith(Parameterized.class)
public class GroovyEngine {

    @Parameterized.Parameters(name = "number test: {index}, groovy script: {0}")
    public static Collection<GroovyExecute> data() {
        E2eConfigurator.configGroovy();
        return _getGroovyExecutes();
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

    private void _test(GroovyExecute execute) {
        execute.getGroovyObject().invokeMethod("test", new Object[0]);
    }

    private void _config(GroovyExecute execute) {
        execute.getGroovyObject().invokeMethod("config", new Object[0]);
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

    private static Collection<GroovyExecute> _getGroovyExecutes() {
        File file = Paths.get("groovy/").toFile();
        List<GroovyExecute> groovyObjects = new ArrayList<>();
        collectFiles(file, groovyObjects);
        return groovyObjects;
    }

    private static void collectFiles(File file, List<GroovyExecute> groovyObjects) {
        for(File groovyFile: Objects.requireNonNull(file.listFiles())) {
            if(groovyFile.isDirectory()) {
                collectFiles(groovyFile, groovyObjects);
            }
            if (groovyFile.isFile()) {
                if(groovyFile.getName().toLowerCase().endsWith(".groovy"))
                    _createGroovyObject(groovyFile).ifPresent(a -> groovyObjects.add(new GroovyExecute(a, groovyFile)));
            }
        }
    }

    private static Optional<GroovyObject> _createGroovyObject(File groovyFile) {
        try {
            URL url = groovyFile.toURI().toURL();
            GroovyScriptEngine engine = new GroovyScriptEngine(new URL[] {url}, GroovyEngine.class.getClassLoader());
            Class<GroovyObject> tests = engine.loadScriptByName(groovyFile.getName());
            return Optional.ofNullable(tests.newInstance());
        } catch (MalformedURLException | IllegalAccessException | ScriptException | InstantiationException | ResourceException e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Getter
    private static class GroovyExecute {
        private final GroovyObject groovyObject;
        private final File groovyFile;

        public GroovyExecute(GroovyObject groovyObject, File groovyFile) {
            this.groovyObject = groovyObject;
            this.groovyFile = groovyFile;
        }

        @Override
        public String toString() {
            return "" + groovyFile.getName();
        }
    }
}
