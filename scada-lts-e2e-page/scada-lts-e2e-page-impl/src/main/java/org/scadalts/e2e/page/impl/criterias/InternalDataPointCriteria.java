package org.scadalts.e2e.page.impl.criterias;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.InternalDataPointAttributeType;

import java.util.Objects;

@Data
@ToString
public class InternalDataPointCriteria extends DataPointCriteria {

    private final @NonNull InternalDataPointAttributeType attributeType;

    @Builder
    protected InternalDataPointCriteria(@NonNull Xid xid, @NonNull DataPointIdentifier identifier, boolean enabled,
                                        @NonNull DataPointProperties dataPointProperties,
                                        @NonNull InternalDataPointAttributeType attributeType) {
        super(xid, identifier, false, enabled, dataPointProperties);
        this.attributeType = attributeType;
    }

    public static InternalDataPointCriteria empty() {
        return InternalDataPointCriteria.builder()
                .attributeType(InternalDataPointAttributeType.NONE)
                .identifier(new DataPointIdentifier("", DataPointType.NONE))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(false)
                .xid(new Xid(""))
                .build();
    }

    public static InternalDataPointCriteria activeThreadCount() {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.ACTIVE_THREAD_COUNT;
        Xid xid = Xid.dataPoint();
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria activeThreadCount(Xid xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.ACTIVE_THREAD_COUNT;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria activeThreadCount(String xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.ACTIVE_THREAD_COUNT;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(new Xid(xid))
                .build();
    }

    public static InternalDataPointCriteria pointValueWriteThreads() {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.POINT_VALUE_WRITE_THREADS;
        Xid xid = Xid.dataPoint();
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria pointValueWriteThreads(Xid xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.POINT_VALUE_WRITE_THREADS;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria pointValueWriteThreads(String xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.POINT_VALUE_WRITE_THREADS;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(new Xid(xid))
                .build();
    }

    public static InternalDataPointCriteria maximumThreadStackHeight() {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.MAXIMUM_THREAD_STACK_HEIGHT;
        Xid xid = Xid.dataPoint();
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria maximumThreadStackHeight(Xid xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.MAXIMUM_THREAD_STACK_HEIGHT;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria maximumThreadStackHeight(String xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.MAXIMUM_THREAD_STACK_HEIGHT;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(new Xid(xid))
                .build();
    }

    public static InternalDataPointCriteria pointValuesToBeWritten() {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.POINT_VALUES_TO_BE_WRITTEN;
        Xid xid = Xid.dataPoint();
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria pointValuesToBeWritten(Xid xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.POINT_VALUES_TO_BE_WRITTEN;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria pointValuesToBeWritten(String xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.POINT_VALUES_TO_BE_WRITTEN;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(new Xid(xid))
                .build();
    }

    public static InternalDataPointCriteria scheduledWorkItems() {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.SCHEDULED_WORK_ITEMS;
        Xid xid = Xid.dataPoint();
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria scheduledWorkItems(Xid xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.SCHEDULED_WORK_ITEMS;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria scheduledWorkItems(String xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.SCHEDULED_WORK_ITEMS;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(new Xid(xid))
                .build();
    }

    public static InternalDataPointCriteria highPriorityWorkItems() {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.HIGH_PRIORITY_WORK_ITEMS;
        Xid xid = Xid.dataPoint();
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria highPriorityWorkItems(Xid xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.HIGH_PRIORITY_WORK_ITEMS;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria highPriorityWorkItems(String xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.HIGH_PRIORITY_WORK_ITEMS;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(new Xid(xid))
                .build();
    }

    public static InternalDataPointCriteria mediumPriorityWorkItems() {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.MEDIUM_PRIORITY_WORK_ITEMS;
        Xid xid = Xid.dataPoint();
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria mediumPriorityWorkItems(Xid xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.MEDIUM_PRIORITY_WORK_ITEMS;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(xid)
                .build();
    }

    public static InternalDataPointCriteria mediumPriorityWorkItems(String xid) {
        InternalDataPointAttributeType attributeType = InternalDataPointAttributeType.MEDIUM_PRIORITY_WORK_ITEMS;
        return InternalDataPointCriteria.builder()
                .attributeType(attributeType)
                .identifier(IdentifierObjectFactory.dataPointName(attributeType))
                .dataPointProperties(DataPointProperties.empty())
                .enabled(true)
                .xid(new Xid(xid))
                .build();
    }

    public InternalDataPointCriteria with(DataPointProperties dataPointProperties) {
        return InternalDataPointCriteria.builder()
                .dataPointProperties(dataPointProperties)
                .enabled(super.isEnabled())
                .xid(super.getXid())
                .identifier(super.getIdentifier())
                .attributeType(attributeType)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InternalDataPointCriteria)) return false;
        InternalDataPointCriteria that = (InternalDataPointCriteria) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}
