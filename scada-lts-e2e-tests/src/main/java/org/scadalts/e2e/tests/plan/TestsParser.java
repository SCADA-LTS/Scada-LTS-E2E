package org.scadalts.e2e.tests.plan;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TestsParser {

    public static List<Class<?>> parseTestClasses(String value) {
        String[] tests = value.split(";");
        return convert(tests);
    }

    private static List<Class<?>> convert(String[] tests) {
        return Stream.of(tests)
                .flatMap(a -> packageNames(a).stream())
                .map(TestsParser::getClass)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static Set<String> packageNames(String name) {
        Set<String> result = Stream.of(PackageTestKeys.values())
                .map(a -> a.getPackage(name))
                .collect(Collectors.toSet());
        result.add(name);
        return result;
    }

    private static Class<?> getClass(String test) {
        try {
            return Class.forName(test);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private enum PackageTestKeys {
        API("org.scadalts.e2e.tests.api."),
        PAGE("org.scadalts.e2e.tests.page."),
        CHECK("org.scadalts.e2e.tests.check.");

        private final String packageKey;

        PackageTestKeys(String key) {
            this.packageKey = key;
        }

        public String getPackage(String name) {
            return packageKey + name;
        }
    }
}
