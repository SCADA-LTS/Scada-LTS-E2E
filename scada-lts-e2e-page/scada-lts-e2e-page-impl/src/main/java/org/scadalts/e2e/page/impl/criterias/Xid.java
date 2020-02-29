package org.scadalts.e2e.page.impl.criterias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static org.scadalts.e2e.page.impl.criterias.XidUtil.generateString;

@Data
@ToString
@EqualsAndHashCode
public class Xid {

    public static final String DP_PREFIX = "DP_";
    private static final String DS_PREFIX = "DS_";

    private final String value;

    public static Xid xidDataPoint() {
        return new Xid(DP_PREFIX + generateString(6, "0123456789"));
    }

    public static Xid xidDataSource() {
        return new Xid(DS_PREFIX + generateString(6, "0123456789"));
    }
}
