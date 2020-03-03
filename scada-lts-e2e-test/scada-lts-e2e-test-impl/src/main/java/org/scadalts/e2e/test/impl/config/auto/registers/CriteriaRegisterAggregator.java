package org.scadalts.e2e.test.impl.config.auto.registers;

import java.util.HashMap;
import java.util.Map;

public enum CriteriaRegisterAggregator {

    INSTANCE;

    private static Map<Class<?>, CriteriaRegister> objects = new HashMap<>();

    public <T> void putRegister(Class<T> clazz, CriteriaRegister criteriaRegister) {
        objects.computeIfAbsent(clazz, key -> objects.put(key, criteriaRegister));
        objects.get(clazz).merge(criteriaRegister);
    }

    public <T> CriteriaRegister getRegister(Class<T> clazz) {
        return objects.get(clazz);
    }

    public <T> boolean containsRegister(Class<T> clazz) {
        return objects.containsKey(clazz);
    }

}
