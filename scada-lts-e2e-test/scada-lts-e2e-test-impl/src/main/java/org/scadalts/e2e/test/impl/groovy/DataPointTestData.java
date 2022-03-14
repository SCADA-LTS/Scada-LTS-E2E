package org.scadalts.e2e.test.impl.groovy;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataPointTestData {
    private final String xid;
    private final String name;
    private final String msg;
    private final double max;
    private final double min;

    private static final DataPointTestData EMPTY = new DataPointTestData("", "", "", Integer.MAX_VALUE, -1);

    public static DataPointTestData empty() {
        return EMPTY;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    @Override
    public String toString() {
        return "xid='" + xid + '\'' +
                ", name='" + name + '\'' +
                (max != -1 ? ", max=" + max : "") +
                (min != -1 ? ", min=" + min : "");
    }
}
