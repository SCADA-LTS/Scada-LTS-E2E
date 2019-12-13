package org.scadalts.e2e.tests.utils;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleArgs {

    public static Properties toProperties(String[] args) {
        Map<String, String> map = toMap(args);
        Properties properties = new Properties();
        properties.putAll(map);
        return properties;
    }

    private static Map<String, String> toMap(String[] args) {
        return Stream.of(args).map(a -> a.split("="))
                    .collect(Collectors.toMap(a -> removeD(a[0]), ConsoleArgs::getValue));
    }

    private static String getValue(String[] val) {
        return val.length <= 1 ? "" : val[1];
    }

    private static String removeD(String s) {
        return s.replace("-D", "");
    }
}
