package org.scadalts.e2e.test.impl.config.auto.registers;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.Command;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class CriteriaRegister implements AutoCloseable {

    private volatile Map<Class<?>, Set<?>> criterias = new ConcurrentHashMap<>();
    private final Class<?> tagetClass;

    public CriteriaRegister(Class<?> tagetClass) {
        this.tagetClass = tagetClass;
    }

    public <T extends CriteriaObject> void register(Class<T> clazz, Set<T> sets) {
        if(!criterias.containsKey(clazz)) {
            criterias.put(clazz, new HashSet<>());
        }
        ((Set<T>)criterias.get(clazz)).addAll(sets);
    }

    public <T extends CriteriaObject> void register(Class<T> clazz, T object) {
        if(!criterias.containsKey(clazz)) {
            criterias.put(clazz, new HashSet<>());
        }
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
                .forEach(key -> {
                        if(criterias.containsKey(key)) {
                            this.get(key).addAll(criteriaRegister.get(key));
                        } else {
                            this.register(key, criteriaRegister.get(key));
                        }
                    });
    }

    @Override
    public void close() {
        CriteriaRegisterAggregator criteriaRegisterAggregator = CriteriaRegisterAggregator.INSTANCE;
        criteriaRegisterAggregator.putRegister(tagetClass, this);
        criterias = Collections.unmodifiableMap(criterias);
    }

    public void clear() {
        criterias.clear();
    }

    public static CriteriaRegister getRegister(Class<?> classTarget, Command<?> executedIfNotExists) {
        CriteriaRegisterAggregator criteriaRegisterAggregator = CriteriaRegisterAggregator.INSTANCE;

        if(!criteriaRegisterAggregator.containsRegister(classTarget)) {
            logger.info("run... {}", executedIfNotExists.getClass().getSimpleName());
            executedIfNotExists.execute();
        }

        return criteriaRegisterAggregator.getRegister(classTarget);
    }

    public static CriteriaRegister removeRegister(Class<?> classTarget) {
        CriteriaRegisterAggregator criteriaRegisterAggregator = CriteriaRegisterAggregator.INSTANCE;
        return criteriaRegisterAggregator.removeRegister(classTarget);
    }

}
