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

    public static DataPointTestData[] getGroovyPoints() {
        String[] points = values("run.test.points");
        return Stream.of(points).map(point -> {
            String[] dates = point.split(":");
            return dates.length != 5 ? DataPointTestData.empty() :
                    DataPointTestData.builder()
                    .xid(dates[0])
                    .name(dates[1])
                    .min(Double.parseDouble(dates[2]))
                    .max(Double.parseDouble(dates[3]))
                    .msg(dates[4])
                    .build();
        }).toArray(DataPointTestData[]::new);
    }

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

        Collection<GroovyExecute> result = new ArrayList<>();
        if(auto) {
            List<File> autoGroovyFiles = new ArrayList<>();
            collectGroovyFiles(path.toFile(), autoGroovyFiles, a -> true);
            result.addAll(_convertToGroovyExecute(autoGroovyFiles));
        } else {
            result.addAll(_convertToGroovyExecute(getGroovyFiles("run.test", path)));
            result.addAll(_convertToGroovyExecuteWithPoints(getGroovyFiles("run.test.with-points", path)));
        }
        logger.info("test runs: {}", result);
        return result;
    }

    private static List<File> getGroovyFiles(String key, Path path) {
        List<File> groovyFiles = new ArrayList<>();
        List<String> runs = names(key);
        collectGroovyFiles(path.toFile(), groovyFiles, file -> runs.contains("groovy" + SEPARATOR + file.getName()));
        return groovyFiles;
    }

    private static List<String> names(String key) {
        String[] tests = values(key);
        logger.info("{}: {}", key, Arrays.toString(tests));
        return Stream.of(tests).map(a -> "groovy" + SEPARATOR + a.replace("/", SEPARATOR)
                .replace("\\", SEPARATOR)).collect(Collectors.toList());
    }

    private static String[] values(String key) {
        Properties properties = toPropeties();
        return properties.getProperty(key, "").split(";");
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
            _createGroovyObject(groovyFile)
                    .ifPresent(groovyObject -> result.add(new GroovyExecute(groovyObject, groovyFile, DataPointTestData.empty())));
        }
        return result;
    }

    private static List<GroovyExecute> _convertToGroovyExecuteWithPoints(List<File> groovyFiles) {
        List<GroovyExecute> result = new ArrayList<>();
        DataPointTestData[] datas = getGroovyPoints();
        for (File groovyFile : groovyFiles) {
            for (DataPointTestData data : datas) {
                _createGroovyObject(groovyFile)
                        .ifPresent(groovyObject -> result.add(new GroovyExecute(groovyObject, groovyFile, data)));
            }
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
