package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.AbstractIdentifier;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;

@Getter
@EqualsAndHashCode(callSuper = true)
public class EventHandlerIdentifier extends AbstractIdentifier {

    private final @NonNull EventHandlerType type;

    public EventHandlerIdentifier(@NonNull String value, @NonNull EventHandlerType type) {
        super(value);
        this.type = type;
    }

    @Override
    public NodeCriteria getNodeCriteria() {
        return NodeCriteria.exactlyTypeAny(this, Tag.span());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
