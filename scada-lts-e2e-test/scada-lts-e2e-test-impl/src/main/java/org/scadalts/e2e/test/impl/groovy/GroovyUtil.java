package org.scadalts.e2e.test.impl.groovy;

import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.utils.FileUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.scadalts.e2e.common.core.groovy.GroovyFileUtil.collectGroovyFiles;
import static org.scadalts.e2e.common.core.utils.FileUtil.getFileFromJar;

@Log4j2
public class GroovyUtil {

    public static Collection<GroovyExecute> getGroovyExecutes() {
        File file = Paths.get("groovy").toFile();
        List<File> groovyFiles = new ArrayList<>();
        collectGroovyFiles(file, groovyFiles);
        return _convertToGroovyExecute(groovyFiles);
    }

    private static List<GroovyExecute> _convertToGroovyExecute(List<File> groovyFiles) {
        List<GroovyExecute> result = new ArrayList<>();
        for(File groovyFile: groovyFiles) {
            _createGroovyObject(groovyFile).ifPresent(a -> result.add(new GroovyExecute(a, groovyFile)));
        }
        return result;
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
}
