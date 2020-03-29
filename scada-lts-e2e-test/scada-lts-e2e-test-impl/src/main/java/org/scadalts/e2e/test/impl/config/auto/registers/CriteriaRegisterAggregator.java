package org.scadalts.e2e.test.impl.config.auto.registers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum CriteriaRegisterAggregator {

    INSTANCE;

    private volatile static Map<Class<?>, CriteriaRegister> objects = new ConcurrentHashMap<>();

    public <T> void putRegister(Class<T> clazz, CriteriaRegister criteriaRegister) {
        if(!objects.containsKey(clazz)) {
            objects.put(clazz, criteriaRegister);
        } else {
            objects.get(clazz).merge(criteriaRegister);
        }
    }

    public <T> CriteriaRegister removeRegister(Class<T> clazz) {
        return objects.remove(clazz);
    }

    public <T> CriteriaRegister getRegister(Class<T> clazz) {
        return objects.get(clazz);
    }

    public <T> boolean containsRegister(Class<T> clazz) {
        return objects.containsKey(clazz);
    }

    public void clear() {
        objects.clear();
    }
}
