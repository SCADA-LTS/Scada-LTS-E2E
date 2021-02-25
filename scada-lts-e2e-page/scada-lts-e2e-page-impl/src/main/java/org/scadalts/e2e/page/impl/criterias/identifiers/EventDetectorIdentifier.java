package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.identifiers.AbstractIdentifier;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;

import static org.scadalts.e2e.page.core.criterias.Tag.tbody;

@Getter
@EqualsAndHashCode(callSuper = true)
public class EventDetectorIdentifier extends AbstractIdentifier {

    private final @NonNull EventDetectorType type;

    public EventDetectorIdentifier(@NonNull String value, @NonNull EventDetectorType type) {
        super(value);
        this.type = type;
    }

    @Override
    public NodeCriteria getNodeCriteria() {
        return NodeCriteria.exactlyTypeAny(this, tbody());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
