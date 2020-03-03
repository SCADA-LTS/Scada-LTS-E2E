package org.scadalts.e2e.test.impl.config.auto.tasks;

public interface Task<T> {
    void execute();
    Class<T> getClassTarget();
}
