package org.scadalts.e2e.test.impl.groovy;

import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.utils.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.scadalts.e2e.common.core.groovy.GroovyFileUtil.collectGroovyFiles;

@Log4j2
public final class GroovyUtil {

    private static final String SEPARATOR = FileSystems.getDefault().getSeparator();

    private GroovyUtil() {}

    public static Collection<GroovyExecute> getGroovyExecutes() {
        List<String> names = names("add.test.paths");
        for (String test : names) {
            FileUtil.getFileFromJar(test).orElse(null);
        }
        Path path = Paths.get("groovy");
        if(!Files.exists(path))
            path.toFile().mkdirs();
        boolean auto = Boolean.parseBoolean(toPropeties().getProperty("run.test.auto", "true"));
        logger.info("automatic script loading: {}", auto);
        List<File> groovyFiles = new ArrayList<>();
        if(auto) {
            collectGroovyFiles(path.toFile(), groovyFiles, a -> true);
        } else {
            List<String> runs = names("run.test.files");
            collectGroovyFiles(path.toFile(), groovyFiles, a -> runs.contains("groovy" + SEPARATOR + a.getName()));
        }
        logger.info("test runs: {}", groovyFiles);
        return _convertToGroovyExecute(groovyFiles);
    }

    private static List<String> names(String key) {
        Properties properties = toPropeties();
        String[] tests = properties.getProperty(key, "").split(";");
        logger.info("{}: {}", key, Arrays.toString(tests));
        return Stream.of(tests).map(a -> "groovy" + SEPARATOR + a.replace("/", SEPARATOR)
                .replace("\\", SEPARATOR)).collect(Collectors.toList());
    }

    public static Properties toPropeties() {
        File config = FileUtil.getFileFromJar("groovy" + SEPARATOR + "groovy-config.properties").orElse(null);
        Properties properties = new Properties();
        if(config == null)
            return properties;
        try {
            try (FileInputStream fileInputStream = new FileInputStream(config)) {
                properties.load(fileInputStream);
            }
        } catch (IOException e) {
            logger.warn(e);
        }
        return properties;
    }

    public static Collection<GroovyExecute> getGroovyExecutes(Class<?> base, List<String> names) {
        List<File> groovyFiles = _groovyFiles(base, names);
        return _convertToGroovyExecute(groovyFiles);
    }

    private static List<File> _groovyFiles(Class<?> base, List<String> names) {
        URL resource = base.getClassLoader().getResource("groovy");
        if(resource == null)
            return Collections.emptyList();
        List<File> groovyFiles = new ArrayList<>();
        String externalForm = resource.toExternalForm();
        logger.info("external form: {}", externalForm);
        File file = new File(externalForm);
        collectGroovyFiles(file, groovyFiles, file1 -> names.contains(file1.getName()));;
        return groovyFiles;
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
            return Optional.of(tests.newInstance());
        } catch (MalformedURLException | IllegalAccessException | ScriptException | InstantiationException | ResourceException e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
