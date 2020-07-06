package org.scadalts.e2e.common.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.scadalts.e2e.common.types.AuthType;
import org.scadalts.e2e.common.types.BrowserRef;
import org.scadalts.e2e.common.types.PageLoadStrategy;
import org.scadalts.e2e.common.types.TestPlan;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Log4j2
public class TypeConstructors {

    private static Map<Class<?>, Function<String, ?>> values = new HashMap<>();

    static {
        values.put(URL.class, TypeConstructors::createURL);
        values.put(File.class, TypeConstructors::createFile);
        values.put(Path.class, TypeConstructors::createPath);
        values.put(String[].class, TypeConstructors::createStringArray);
        values.put(PageLoadStrategy.class, TypeConstructors::createPageLoadStrategy);
        values.put(AuthType.class, TypeConstructors::createAuthType);
        values.put(BrowserRef.class, TypeConstructors::createBrowserRef);
        values.put(Level.class, TypeConstructors::createLevel);
        values.put(TestPlan.class, TypeConstructors::createTestPlan);
        values.put(long.class, Long::parseLong);
        values.put(int.class, Integer::parseInt);
        values.put(boolean.class, Boolean::parseBoolean);
        values.put(String.class, Function.identity());
        values.put(Set.class, SendToUtil::parse);
    }

    public static <T> T create(Class<T> key, String value) {
        try {
            logger.debug("key: {}, value: {}", key.getName(), value);
            if(key.equals(Class.class)) {
                return null;
            }
            Function<String, T> converter = (Function<String, T>)values.get(key);
            return converter.apply(value);
        } catch (Throwable throwable) {
            logger.warn(throwable.getMessage(), throwable);
            throw throwable;
        }
    }

    private static URL createURL(String value) {
        try {
            return new URL(value);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private static Path createPath(String value) {
        return Paths.get(value);
    }

    private static File createFile(String value) {
        return new File(value);
    }

    private static AuthType createAuthType(String value) {
        return AuthType.valueOf(value.toUpperCase());
    }

    private static BrowserRef createBrowserRef(String value) {
        return BrowserRef.valueOf(value.toUpperCase());
    }

    private static PageLoadStrategy createPageLoadStrategy(String value) {
        return PageLoadStrategy.valueOf(value.toUpperCase());
    }

    private static Level createLevel(String value) {
        return Level.valueOf(value.toUpperCase());
    }

    private static TestPlan createTestPlan(String value) {
        return TestPlan.valueOf(value.toUpperCase());
    }

    private static String[] createStringArray(String value) {
        return value.split(";");
    }
}
