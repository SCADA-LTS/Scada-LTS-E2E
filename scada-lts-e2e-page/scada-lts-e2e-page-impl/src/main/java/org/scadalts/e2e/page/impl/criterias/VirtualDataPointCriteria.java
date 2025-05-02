package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointLoggingProperties;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.ChangeType;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.LoggingType;

import java.util.Objects;

@Data
@ToString
public class VirtualDataPointCriteria extends DataPointCriteria {

    private final @NonNull ChangeType changeType;
    private final @NonNull String startValue;

    @Builder
    protected VirtualDataPointCriteria(@NonNull Xid xid, @NonNull DataPointIdentifier identifier,
                                       @NonNull ChangeType changeType, @NonNull String startValue,
                                       boolean settable, boolean enabled,
                                       @NonNull DataPointProperties dataPointProperties) {
        super(xid, identifier, settable, enabled, dataPointProperties);
        this.changeType = changeType;
        this.startValue = startValue;
    }

    public static VirtualDataPointCriteria empty() {
        return VirtualDataPointCriteria.builder()
                .changeType(ChangeType.NONE)
                .identifier(new DataPointIdentifier("", DataPointType.NONE))
                .dataPointProperties(DataPointProperties.empty())
                .startValue("")
                .settable(false)
                .enabled(false)
                .xid(new Xid(""))
                .build();
    }

    public static VirtualDataPointCriteria binaryAlternate() {
        DataPointType dataPointType = DataPointType.BINARY;
        ChangeType changeType = ChangeType.ALTERNATE;
        Xid xid = Xid.dataPoint();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue("true")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria binaryNoChange() {
        DataPointType dataPointType = DataPointType.BINARY;
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.dataPoint();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue("true")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria alternate(DataPointIdentifier identifier) {
        ChangeType changeType = ChangeType.ALTERNATE;
        Xid xid = Xid.dataPoint();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue("true")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria internal(String name, Xid xid) {
        ChangeType changeType = ChangeType.NONE;
        DataPointType dataPointType = DataPointType.NONE;
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(new DataPointIdentifier(name, dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue(dataPointType.getDefaultValue())
                .settable(false)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria numericNoChange() {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.dataPoint();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue("123")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria noChange(DataPointType dataPointType, String startValue) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.dataPoint();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue(startValue)
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria noChange(DataPointType dataPointType, String startValue,
                                                    DataPointProperties dataPointProperties) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.dataPoint();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(dataPointProperties)
                .startValue(startValue)
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria numericNoChange(int startValue) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.dataPoint();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria numericNoChange(Xid xid, int startValue) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria numericNoChange(Xid xid) {
        DataPointType dataPointType = DataPointType.NUMERIC;
        ChangeType changeType = ChangeType.NO_CHANGE;
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue("123456")
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria noChange(Xid xid, DataPointIdentifier identifier, String startValue) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria noChange(Xid xid, DataPointIdentifier identifier) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue(identifier.getType().getDefaultValue())
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria noChange(String xid, DataPointIdentifier identifier) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue(identifier.getType().getDefaultValue())
                .settable(true)
                .enabled(true)
                .xid(new Xid(xid))
                .build();
    }

    public static VirtualDataPointCriteria noChange(DataPointIdentifier identifier) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.dataPoint();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue(identifier.getType().getDefaultValue())
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria noChange(DataPointIdentifier identifier, boolean enabled) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.dataPoint();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue(identifier.getType().getDefaultValue())
                .settable(true)
                .enabled(enabled)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria noChange(DataPointIdentifier identifier, String startValue) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.dataPoint();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria noChangeAllDataLogging(DataPointIdentifier identifier, String startValue) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.dataPoint();
        DataPointProperties dataPointProperties = DataPointProperties.builder()
                .loggingProperties(DataPointLoggingProperties.builder()
                        .loggingType(LoggingType.ALL).build())
                .build();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(dataPointProperties)
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria noChange(DataPointIdentifier identifier, String startValue,
                                                    DataPointLoggingProperties dataPointLoggingProperties) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.dataPoint();
        DataPointProperties dataPointProperties = DataPointProperties.builder()
                .loggingProperties(dataPointLoggingProperties)
                .build();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(dataPointProperties)
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria noChange(DataPointIdentifier identifier, String startValue, boolean enabled) {
        ChangeType changeType = ChangeType.NO_CHANGE;
        Xid xid = Xid.dataPoint();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(identifier)
                .dataPointProperties(DataPointProperties.empty())
                .startValue(String.valueOf(startValue))
                .settable(true)
                .enabled(enabled)
                .xid(xid)
                .build();
    }

    public static VirtualDataPointCriteria criteria(DataPointType dataPointType, String startValue, ChangeType changeType) {
        Xid xid = Xid.dataPoint();
        return VirtualDataPointCriteria.builder()
                .changeType(changeType)
                .identifier(IdentifierObjectFactory.dataPointName(dataPointType))
                .dataPointProperties(DataPointProperties.empty())
                .startValue(startValue)
                .settable(true)
                .enabled(true)
                .xid(xid)
                .build();
    }

    public VirtualDataPointCriteria with(DataPointProperties dataPointProperties) {
        return VirtualDataPointCriteria.builder()
                .dataPointProperties(dataPointProperties)
                .enabled(super.isEnabled())
                .xid(super.getXid())
                .settable(super.isSettable())
                .changeType(changeType)
                .identifier(super.getIdentifier())
                .startValue(startValue)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VirtualDataPointCriteria)) return false;
        VirtualDataPointCriteria that = (VirtualDataPointCriteria) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}
