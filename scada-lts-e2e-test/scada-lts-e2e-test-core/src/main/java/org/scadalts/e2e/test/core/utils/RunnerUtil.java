package org.scadalts.e2e.test.core.utils;

import lombok.extern.log4j.Log4j2;
import org.junit.runners.Suite;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
public class RunnerUtil {
    public static boolean isSuite(Class<?> clazz) {
        Set<?> annotations = Arrays.stream(clazz.getDeclaredAnnotations()).map(Annotation::getClass)
                .collect(Collectors.toSet());
        logger.info("annotations: {}", annotations);
        return annotations.contains(Suite.SuiteClasses.class);
    }
}
