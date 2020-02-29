package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.utils.XpathFactory;
import org.scadalts.e2e.page.impl.dicts.ChangeType;
import org.scadalts.e2e.page.impl.dicts.DataPointType;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class DataPointCriteria implements CriteriaObject {

    private final @NonNull Xid xid;
    private final @NonNull DataPointIdentifier identifier;
    private final @NonNull DataPointType type;
    private final @NonNull ChangeType changeType;
    private final @NonNull String startValue;

    private DataPointCriteria(Xid xid, DataPointIdentifier identifier, DataPointType type, ChangeType changeType, String startValue) {
        this.xid = xid == null ? Xid.xidDataPoint() : xid;
        this.identifier = identifier;
        this.type = type;
        this.changeType = changeType;
        this.startValue = startValue;
    }

    public static DataPointCriteria binaryAlternate() {
        String dataPointName = "dp_test" + System.nanoTime();
        DataPointType dataPointType = DataPointType.BINARY;
        ChangeType changeType = ChangeType.ALTERNATE;
        Xid xid = Xid.xidDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(new DataPointIdentifier(dataPointName))
                .startValue("true")
                .xid(xid)
                .build();
    }

    public static DataPointCriteria noChange(DataPointType dataPointType, String startValue) {
        String dataPointName = "dp_test" + System.nanoTime();
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(new DataPointIdentifier(dataPointName))
                .startValue(startValue)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria numericNoChange(int startValue) {
        String dataPointName = "dp_test" + System.nanoTime();
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(new DataPointIdentifier(dataPointName))
                .startValue(String.valueOf(startValue))
                .xid(xid)
                .build();
    }

    public static DataPointCriteria numericNoChange(DataPointIdentifier identifier) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(identifier)
                .startValue("123")
                .xid(xid)
                .build();
    }

    public static DataPointCriteria numericNoChange(DataPointIdentifier identifier, int startValue) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(identifier)
                .startValue(String.valueOf(startValue))
                .xid(xid)
                .build();
    }

    public static DataPointCriteria criteria(DataPointType dataPointType, String startValue, ChangeType changeType) {
        String dataPointName = "dp_test" + System.nanoTime();
        Xid xid = Xid.xidDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(new DataPointIdentifier(dataPointName))
                .startValue(startValue)
                .xid(xid)
                .build();
    }

    @Override
    public String getXpath() {
        return XpathFactory.xpath("tr", identifier.getValue(), type.getName());
    }
}
