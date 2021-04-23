package org.scadalts.e2e.page.impl.criterias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static org.scadalts.e2e.page.impl.criterias.XidUtil.generateString;

@Data
@ToString
@EqualsAndHashCode
public class Xid {

    public static final String DATA_POINT_PREFIX = "DP_TEST_";
    private static final String DATA_SOURCE_PREFIX = "DS_TEST_";
    private static final String GRAPHICAL_VIEW_PREFIX = "GV_TEST_";
    private static final String POINT_LINK_PREFIX = "PL_TEST_";
    private static final String POINT_EVENT_DETECTOR_PREFIX = "PED_TEST_";
    private static final String SCRIPT_PREFIX = "SC_TEST_";
    private static final String EVENT_HANDLER_PREFIX = "EH_TEST_";

    private static final Xid EMPTY = new Xid("");

    private final String value;

    public static Xid dataPoint() {
        return new Xid(DATA_POINT_PREFIX + _generate());
    }

    public static Xid dataSource() {
        return new Xid(DATA_SOURCE_PREFIX + _generate());
    }

    public static Xid graphicalView() {
        return new Xid(GRAPHICAL_VIEW_PREFIX + _generate());
    }

    public static Xid pointLink() {
        return new Xid(POINT_LINK_PREFIX + _generate());
    }

    public static Xid eventDetector() {
        return new Xid(POINT_EVENT_DETECTOR_PREFIX + _generate());
    }

    public static Xid empty() {
        return EMPTY;
    }

    public static Xid script() {
        return new Xid(SCRIPT_PREFIX + _generate());
    }

    public static Xid eventHandler() {
        return new Xid(EVENT_HANDLER_PREFIX + _generate());
    }

    private static String _generate() {
        return generateString(6, "0123456789");
    }

    @Override
    public String toString() {
        return value;
    }
}
