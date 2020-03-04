package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventDetectorIdentifier;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@ToString
@EqualsAndHashCode
public class EventDetectorCriteria implements CriteriaObject, GetXid {

    @Deprecated
    private final @NonNull Xid xid;
    private final @NonNull EventDetectorIdentifier identifier;
    private final @NonNull EventDetectorType type;
    private final @NonNull AlarmLevel alarmLevel;
    private final @NonNull @Singular List<EventHandlerCriteria> eventHandlerCriterias;
    private final @NonNull DataSourcePointCriteria dataSourcePointCriteria;

    public static EventDetectorCriteria changeAlarmLevelNone(DataSourcePointCriteria dataSourcePointCriteria) {
        Xid xid = Xid.xidForEventDetector();
        return EventDetectorCriteria.builder()
                .xid(xid)
                .identifier(IdentifierObjectFactory.eventDetectorName())
                .type(EventDetectorType.CHANGE)
                .alarmLevel(AlarmLevel.NONE)
                .eventHandlerCriterias(Collections.emptyList())
                .dataSourcePointCriteria(dataSourcePointCriteria)
                .build();
    }

    public static EventDetectorCriteria criteria(EventDetectorIdentifier identifier, EventDetectorType type,
                                                 AlarmLevel alarmLevel, DataSourcePointCriteria dataSourcePointCriteria) {
        Xid xid = Xid.xidForEventDetector();
        return EventDetectorCriteria.builder()
                .xid(xid)
                .identifier(identifier)
                .type(type)
                .alarmLevel(alarmLevel)
                .eventHandlerCriterias(Collections.emptyList())
                .dataSourcePointCriteria(dataSourcePointCriteria)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventDetectorCriteria)) return false;
        EventDetectorCriteria that = (EventDetectorCriteria) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}
