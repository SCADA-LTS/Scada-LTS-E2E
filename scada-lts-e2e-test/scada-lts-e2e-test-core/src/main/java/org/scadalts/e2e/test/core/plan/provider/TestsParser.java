package org.scadalts.e2e.test.core.plan.provider;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TestsParser {

    static List<Class<?>> parseTestClasses(String[] tests) {
        return Stream.of(tests)
                .flatMap(a -> packageNames(a).stream())
                .map(TestsParser::_getClass)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    static Set<String> packageNames(String name) {
        Set<String> result = Stream.of(PackageTestKeys.values())
                .map(a -> a.getPackage(name))
                .collect(Collectors.toSet());
        result.add(name);
        return result;
    }

    private static Optional<Class<?>> _getClass(String test) {
        try {
            return Optional.ofNullable(Class.forName(test));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
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
