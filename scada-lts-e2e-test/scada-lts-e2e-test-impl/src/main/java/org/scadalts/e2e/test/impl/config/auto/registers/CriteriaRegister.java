package org.scadalts.e2e.test.impl.config.auto.registers;

import org.scadalts.e2e.page.core.criterias.CriteriaObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CriteriaRegister {

    public Map<Class<?>, Set<?>> criterias = new HashMap<>();

    public <T extends CriteriaObject> void register(Class<T> clazz, Set<T> sets) {
        criterias.computeIfAbsent(clazz, a -> criterias.put(a, new HashSet<>()));
        ((Set<T>)criterias.get(clazz)).addAll(sets);
    }

    public <T extends CriteriaObject> void register(Class<T> clazz, T object) {
        criterias.computeIfAbsent(clazz, a -> criterias.put(a, new HashSet<>()));
        ((Set<T>)criterias.get(clazz)).add(object);
    }

    public <T extends CriteriaObject> Set<T> get(Class<T> clazz) {
        return Set.class.<T>cast(criterias.get(clazz));
    }

    public <T extends CriteriaObject> Set<Class<T>> keySet() {
        return Set.class.<T>cast(criterias.keySet());
    }

    public void merge(CriteriaRegister criteriaRegister) {
        criteriaRegister.keySet()
                .stream()
                .peek(key -> {
                        if(criterias.containsKey(key)) {
                            this.get(key).addAll(criteriaRegister.get(key));
                        } else {
                            this.register(key, criteriaRegister.get(key));
                        }
                    })
                .close();
    }
}
