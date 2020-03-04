package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventHandlerIdentifier;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;

import java.util.Objects;

@Data
@Builder
@ToString
@EqualsAndHashCode
public class EventHandlerCriteria implements CriteriaObject, GetXid {

    @Deprecated
    private final @NonNull Xid xid;
    private final @NonNull EventHandlerIdentifier identifier;
    private final @NonNull ScriptCriteria activeScript;
    private final @NonNull ScriptCriteria inactiveScript;
    private final @NonNull EventDetectorCriteria eventDetectorCriteria;
    private final @NonNull EventHandlerType type;
    private final boolean disabled;

    private EventHandlerCriteria(@NonNull Xid xid, @NonNull EventHandlerIdentifier identifier, ScriptCriteria activeScript, ScriptCriteria inactiveScript, @NonNull EventDetectorCriteria eventDetectorCriteria, @NonNull EventHandlerType type, boolean disabled) {
        this.xid = xid;
        this.identifier = identifier;
        this.activeScript = activeScript;
        this.inactiveScript = inactiveScript;
        this.eventDetectorCriteria = eventDetectorCriteria;
        this.type = type;
        this.disabled = disabled;
    }

    public static EventHandlerCriteria script(EventHandlerIdentifier identifier, EventDetectorCriteria eventDetectorCriteria, ScriptCriteria scriptCriteria) {
        return EventHandlerCriteria.builder()
                .eventDetectorCriteria(eventDetectorCriteria)
                .activeScript(scriptCriteria)
                .inactiveScript(ScriptCriteria.empty())
                .identifier(identifier)
                .type(EventHandlerType.SCRIPT)
                .xid(Xid.xidForEventHandler())
                .build();
    }

    public static EventHandlerCriteria script(EventDetectorCriteria eventDetectorCriteria, ScriptCriteria activeScript) {
        return EventHandlerCriteria.builder()
                .eventDetectorCriteria(eventDetectorCriteria)
                .activeScript(activeScript)
                .inactiveScript(ScriptCriteria.empty())
                .identifier(IdentifierObjectFactory.eventHandlerName())
                .type(EventHandlerType.SCRIPT)
                .xid(Xid.xidForEventHandler())
                .build();
    }

    public static EventHandlerCriteria script(EventDetectorCriteria eventDetectorCriteria, ScriptCriteria activeScript,
                                              ScriptCriteria inactiveScript) {
        return EventHandlerCriteria.builder()
                .eventDetectorCriteria(eventDetectorCriteria)
                .activeScript(activeScript)
                .inactiveScript(inactiveScript)
                .identifier(IdentifierObjectFactory.eventHandlerName())
                .type(EventHandlerType.SCRIPT)
                .xid(Xid.xidForEventHandler())
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
                .type(EventHandlerType.SCRIPT)
                .xid(Xid.xidForEventHandler())
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
}
