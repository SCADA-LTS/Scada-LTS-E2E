package org.scadalts.e2e.page.impl.criterias;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointLoggingProperties;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.ChangeType;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.LoggingType;

import java.util.Objects;

@Data
@Builder
@ToString
public class DataPointCriteria implements CriteriaObject, GetXid {

    private final @NonNull Xid xid;
    private final @NonNull DataPointIdentifier identifier;
    private final @NonNull ChangeType changeType;
    private final @NonNull String startValue;
    private final boolean settable;
    private final boolean enabled;
    private final @NonNull DataPointProperties dataPointProperties;


    protected DataPointCriteria(@NonNull Xid xid, @NonNull DataPointIdentifier identifier, @NonNull ChangeType changeType,
                              @NonNull String startValue, boolean settable, boolean enabled,
                              @NonNull DataPointProperties dataPointProperties) {
        this.xid = xid;
        this.identifier = identifier;
        this.changeType = changeType;
        this.startValue = startValue;
        this.settable = settable;
        this.enabled = enabled;
        this.dataPointProperties = dataPointProperties;
    }

    public static DataPointCriteria empty() {
        return DataPointCriteria.builder()
                .changeType(ChangeType.NONE)
                .identifier(new DataPointIdentifier("", DataPointType.NONE))
                .dataPointProperties(DataPointProperties.empty())
                .startValue("")
                .settable(false)
                .enabled(false)
                .xid(new Xid(""))
                .build();
    }

    public static DataPointCriteria binaryAlternate() {
        DataPointType dataPointType = DataPointType.BINARY;
        ChangeType changeType = ChangeType.ALTERNATE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue("true")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria binaryNoChange() {
        DataPointType dataPointType = DataPointType.BINARY;
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue("true")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria alternate(DataPointIdentifier identifier) {
        ChangeType changeType = ChangeType.ALTERNATE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue("true")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria numericNoChange() {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue("123")
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
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue(startValue)
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria noChange(DataPointType dataPointType, String startValue,
                                             DataPointProperties dataPointProperties) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(dataPointProperties)
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
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
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
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
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
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue("123456")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria noChange(Xid xid, DataPointIdentifier identifier, String startValue) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria noChange(Xid xid, DataPointIdentifier identifier) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue("123")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }


    public static DataPointCriteria noChange(DataPointIdentifier identifier) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue("123")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria noChange(DataPointIdentifier identifier, boolean enabled) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue("123")
                .settable(true)
                .enabled(enabled)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria noChange(DataPointIdentifier identifier, String startValue) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria noChangeAllDataLogging(DataPointIdentifier identifier, String startValue) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidForDataPoint();
        DataPointProperties dataPointProperties = DataPointProperties.builder()
                .loggingProperties(DataPointLoggingProperties.builder()
                        .loggingType(LoggingType.ALL).build())
                .build();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(dataPointProperties)
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria noChange(DataPointIdentifier identifier, String startValue,
                                             DataPointLoggingProperties dataPointLoggingProperties) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidForDataPoint();
        DataPointProperties dataPointProperties = DataPointProperties.builder()
                .loggingProperties(dataPointLoggingProperties)
                .build();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(dataPointProperties)
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria noChange(DataPointIdentifier identifier, String startValue, boolean enabled) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(enabled)
                .xid(xid)
                .build();
    }

    public static DataPointCriteria criteria(DataPointType dataPointType, String startValue, ChangeType changeType) {
        Xid xid = Xid.xidForDataPoint();
        return DataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue(startValue)
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public DataPointCriteria with(DataPointProperties dataPointProperties) {
        return DataPointCriteria.builder()
                .dataPointProperties(dataPointProperties)
                .enabled(enabled)
                .xid(xid)
                .settable(settable)
                .changeType(changeType)
                .identifier(identifier)
                .startValue(startValue)
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
