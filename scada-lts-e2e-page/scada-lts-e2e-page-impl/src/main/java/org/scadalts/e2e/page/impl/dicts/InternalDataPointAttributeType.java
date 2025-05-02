package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;

@Getter
public enum InternalDataPointAttributeType {

    POINT_VALUES_TO_BE_WRITTEN("Point values to be written", 1),
    POINT_VALUE_WRITE_THREADS("Point value write threads", 2),
    HIGH_PRIORITY_WORK_ITEMS("High priority work items", 3),
    MEDIUM_PRIORITY_WORK_ITEMS("Medium priority work items", 4),
    SCHEDULED_WORK_ITEMS("Scheduled work items", 5),
    MAXIMUM_THREAD_STACK_HEIGHT("Maximum thread stack height", 6),
    ACTIVE_THREAD_COUNT("Active thread count", 7),
    NONE("", -1);

    private final String name;
    private final int id;

    InternalDataPointAttributeType(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
