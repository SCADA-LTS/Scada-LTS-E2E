package org.scadalts.e2e.test.core.plan.runner;

import org.junit.runner.Description;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class E2eDescription {

    private final Description description;

    E2eDescription(Description description) {
        this.description = description;
    }

    public String getDisplayName() {
        return description.getDisplayName();
    }

    public List<E2eDescription> getChildren() {
        return description.getChildren()
                .stream()
                .map(E2eDescription::new)
                .collect(Collectors.toList());
    }

    public boolean isSuite() {
        return description.isSuite();
    }

    public boolean isTest() {
        return description.isTest();
    }

    public int testCount() {
        return description.testCount();
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return description.equals(obj);
    }

    @Override
    public String toString() {
        return description.toString();
    }

    public boolean isEmpty() {
        return description.isEmpty();
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        return description.getAnnotation(annotationType);
    }

    public Collection<Annotation> getAnnotations() {
        return description.getAnnotations();
    }

    public Class<?> getTestClass() {
        return description.getTestClass();
    }

    public String getClassName() {
        return description.getClassName();
    }

    public String getMethodName() {
        return description.getMethodName();
    }
}
