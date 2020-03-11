package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.dicts.ChangeType;
import org.scadalts.e2e.page.impl.dicts.DataPointType;

import java.util.Objects;

@Data
@Builder
@ToString
@EqualsAndHashCode
public class DataPointCriteria implements CriteriaObject, GetXid {

    @Deprecated
    private final @NonNull Xid xid;
    private final @NonNull DataPointIdentifier identifier;
    private final @NonNull DataPointType type;
    private final @NonNull ChangeType changeType;
    private final @NonNull String startValue;
    private final boolean settable;
    private final boolean enabled;

    public DataPointCriteria(@NonNull Xid xid, @NonNull DataPointIdentifier identifier, @NonNull DataPointType type,
                             @NonNull ChangeType changeType, @NonNull String startValue,
                             boolean settable, boolean enabled) {
        this.xid = xid;
        this.identifier = identifier;
        this.type = type;
        this.changeType = changeType;
        this.startValue = startValue;
        this.settable = settable;
        this.enabled = enabled;
    }

    public static DataPointCriteria binaryAlternate() {
        DataPointType dataPointType = DataPointType.BINARY;
        ChangeType changeType = ChangeType.ALTERNATE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(IdentifierObjectFactory.dataPointName())
                .startValue("true")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria noChange(DataPointType dataPointType, String startValue) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(IdentifierObjectFactory.dataPointName())
                .startValue(startValue)
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria numericNoChange(int startValue) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(IdentifierObjectFactory.dataPointName())
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria numericNoChange(Xid xid, int startValue) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(IdentifierObjectFactory.dataPointName())
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria numericNoChange(Xid xid) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(IdentifierObjectFactory.dataPointName())
                .startValue("123456")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria numericNoChange(Xid xid, DataPointIdentifier identifier, int startValue) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(identifier)
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria numericNoChange(Xid xid, DataPointIdentifier identifier) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(identifier)
                .startValue("123")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }


    public static DataPointCriteria numericNoChange(DataPointIdentifier identifier) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(identifier)
                .startValue("123")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria numericNoChange(DataPointIdentifier identifier, int startValue) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(identifier)
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria criteria(DataPointType dataPointType, String startValue, ChangeType changeType) {
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(IdentifierObjectFactory.dataPointName())
                .startValue(startValue)
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria binaryAlternate(DataPointIdentifier identifier, boolean startValue) {
        DataPointType dataPointType = DataPointType.BINARY;
        ChangeType changeType = ChangeType.ALTERNATE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .type(dataPointType)
                .identifier(identifier)
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataPointCriteria)) return false;
        DataPointCriteria that = (DataPointCriteria) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}
