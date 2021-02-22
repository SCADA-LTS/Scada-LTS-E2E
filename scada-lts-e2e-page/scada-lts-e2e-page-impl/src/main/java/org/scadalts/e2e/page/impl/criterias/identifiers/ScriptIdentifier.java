package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.AbstractIdentifier;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;

@EqualsAndHashCode(callSuper = true)
public class ScriptIdentifier extends AbstractIdentifier {
    public ScriptIdentifier(@NonNull String value) {
        super(value);
    }

    @Override
    public NodeCriteria getNodeCriteria() {
        return NodeCriteria.exactlyTypeAny(this, Tag.td());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
