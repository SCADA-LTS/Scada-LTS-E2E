package org.scadalts.e2e.test.core.plans.providers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TestsParser {

    static List<Class<?>> parse(String... tests) {
        return Stream.of(tests)
                .map(String::trim)
                .map(TestsParser::_getClass)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static Optional<Class<?>> _getClass(String test) {
        try {
            return Optional.ofNullable(Class.forName(test));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }
}
