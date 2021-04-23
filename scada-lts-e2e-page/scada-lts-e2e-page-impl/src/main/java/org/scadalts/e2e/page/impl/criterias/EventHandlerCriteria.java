package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventHandlerIdentifier;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;

import java.util.Objects;

import static org.scadalts.e2e.page.core.criterias.Tag.div;
import static org.scadalts.e2e.page.core.criterias.Tag.span;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.clazz;

@Data
@Builder
@ToString(exclude = "eventDetectorCriteria")
public class EventHandlerCriteria implements CriteriaObject, GetXid {

    private final @NonNull Xid xid;
    private final @NonNull EventHandlerIdentifier identifier;
    private final @NonNull ScriptCriteria activeScript;
    private final @NonNull ScriptCriteria inactiveScript;
    private final @NonNull EventDetectorCriteria eventDetectorCriteria;
    private final boolean disabled;

    private EventHandlerCriteria(@NonNull Xid xid, @NonNull EventHandlerIdentifier identifier,
                                 @NonNull ScriptCriteria activeScript, @NonNull ScriptCriteria inactiveScript,
                                 @NonNull EventDetectorCriteria eventDetectorCriteria, boolean disabled) {
        this.xid = xid;
        this.identifier = identifier;
        this.activeScript = activeScript;
        this.inactiveScript = inactiveScript;
        this.eventDetectorCriteria = eventDetectorCriteria;
        this.disabled = disabled;
    }

    public static EventHandlerCriteria activeScript(EventHandlerIdentifier identifier, EventDetectorCriteria eventDetectorCriteria, ScriptCriteria scriptCriteria) {
        return EventHandlerCriteria.builder()
                .eventDetectorCriteria(eventDetectorCriteria)
                .activeScript(scriptCriteria)
                .inactiveScript(ScriptCriteria.empty())
                .identifier(identifier)
                .xid(Xid.eventHandler())
                .build();
    }

    public static EventHandlerCriteria script(EventDetectorCriteria eventDetectorCriteria, ScriptCriteria activeScript) {
        return EventHandlerCriteria.builder()
                .eventDetectorCriteria(eventDetectorCriteria)
                .activeScript(activeScript)
                .inactiveScript(ScriptCriteria.empty())
                .identifier(IdentifierObjectFactory.eventHandlerName(EventHandlerType.SCRIPT))
                .xid(Xid.eventHandler())
                .build();
    }

    public static EventHandlerCriteria script(EventDetectorCriteria eventDetectorCriteria, ScriptCriteria activeScript,
                                              ScriptCriteria inactiveScript) {
        return EventHandlerCriteria.builder()
                .eventDetectorCriteria(eventDetectorCriteria)
                .activeScript(activeScript)
                .inactiveScript(inactiveScript)
                .identifier(IdentifierObjectFactory.eventHandlerName(EventHandlerType.SCRIPT))
                .xid(Xid.eventHandler())
                .build();
    }

    public static EventHandlerCriteria script(EventHandlerIdentifier eventHandlerIdentifier,
                                              EventDetectorCriteria eventDetectorCriteria,
                                              ScriptCriteria activeScript,
                                              ScriptCriteria inactiveScript) {
        return EventHandlerCriteria.builder()
                .eventDetectorCriteria(eventDetectorCriteria)
                .activeScript(activeScript)
                .inactiveScript(inactiveScript)
                .identifier(eventHandlerIdentifier)
                .xid(Xid.eventHandler())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventHandlerCriteria)) return false;
        EventHandlerCriteria that = (EventHandlerCriteria) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }

    @Override
    public NodeCriteria[] getNodeCriteria() {
        DataSourcePointCriteria dataSourcePointCriteria = eventDetectorCriteria.getDataSourcePointCriteria();
        NodeCriteria nodeCriteria = NodeCriteria.exactly(dataSourcePointCriteria.getIdentifier(), div(), clazz("dojoTreeNode"));
        NodeCriteria nodeCriteria2 = NodeCriteria.exactlyTypeAny(eventDetectorCriteria.getIdentifier(), div());
        NodeCriteria nodeCriteria3 = NodeCriteria.exactlyTypeAny(identifier, span());
        return new NodeCriteria[]{nodeCriteria, nodeCriteria2, nodeCriteria3};
    }
}
