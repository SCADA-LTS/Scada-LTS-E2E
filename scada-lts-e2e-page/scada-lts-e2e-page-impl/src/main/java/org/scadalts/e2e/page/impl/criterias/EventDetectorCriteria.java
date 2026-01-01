package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventDetectorIdentifier;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.scadalts.e2e.page.core.criterias.Tag.div;
import static org.scadalts.e2e.page.core.criterias.Tag.span;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.clazz;

@Data
@Builder
@ToString(exclude = "dataSourcePointCriteria")
public class EventDetectorCriteria implements CriteriaObject, GetXid {

    private final @NonNull Xid xid;
    private final @NonNull EventDetectorIdentifier identifier;
    private final @NonNull AlarmLevel alarmLevel;
    private final @NonNull @Singular List<EventHandlerCriteria> eventHandlerCriterias;
    private final @NonNull DataSourcePointCriteria<?, ?> dataSourcePointCriteria;

    public static EventDetectorCriteria changeAlarmLevelNone(DataSourcePointCriteria<?, ?> dataSourcePoints) {
        Xid xid = Xid.eventDetector();
        return EventDetectorCriteria.builder()
                .xid(xid)
                .identifier(IdentifierObjectFactory.eventDetectorName(EventDetectorType.CHANGE))
                .alarmLevel(AlarmLevel.NONE)
                .eventHandlerCriterias(Collections.emptyList())
                .dataSourcePointCriteria(dataSourcePoints)
                .build();
    }

    public static EventDetectorCriteria criteria(EventDetectorIdentifier identifier,
                                                 AlarmLevel alarmLevel,
                                                 DataSourcePointCriteria<?, ?> dataSourcePointCriteria) {
        Xid xid = Xid.eventDetector();
        return EventDetectorCriteria.builder()
                .xid(xid)
                .identifier(identifier)
                .alarmLevel(alarmLevel)
                .eventHandlerCriterias(Collections.emptyList())
                .dataSourcePointCriteria(dataSourcePointCriteria)
                .build();
    }

    public static EventDetectorCriteria change(DataSourcePointCriteria<?, ?> dataSourcePointCriteria,
                                               AlarmLevel alarmLevel) {
        Xid xid = Xid.eventDetector();
        return EventDetectorCriteria.builder()
                .xid(xid)
                .identifier(IdentifierObjectFactory.eventDetectorName(EventDetectorType.CHANGE))
                .alarmLevel(alarmLevel)
                .eventHandlerCriterias(Collections.emptyList())
                .dataSourcePointCriteria(dataSourcePointCriteria)
                .build();
    }

    public static EventDetectorCriteria criteria(EventDetectorType eventDetectorType,
                                                 DataSourcePointCriteria<?, ?> dataSourcePointCriteria,
                                                 AlarmLevel alarmLevel) {
        Xid xid = Xid.eventDetector();
        return EventDetectorCriteria.builder()
                .xid(xid)
                .identifier(IdentifierObjectFactory.eventDetectorName(eventDetectorType))
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

    @Override
    public NodeCriteria[] getNodeCriteria() {
        NodeCriteria nodeCriteria = NodeCriteria.exactly(dataSourcePointCriteria.getIdentifier(), div(), clazz("dojoTreeNode"));
        NodeCriteria nodeCriteria2 = NodeCriteria.exactlyTypeAny(identifier, span());
        return new NodeCriteria[]{nodeCriteria, nodeCriteria2};
    }
}
