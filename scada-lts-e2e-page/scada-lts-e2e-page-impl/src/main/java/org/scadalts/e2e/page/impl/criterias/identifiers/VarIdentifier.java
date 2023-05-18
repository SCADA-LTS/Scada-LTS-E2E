package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.scadalts.e2e.page.core.criterias.identifiers.AbstractIdentifier;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;

import static org.scadalts.e2e.page.core.criterias.Tag.*;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.*;

@EqualsAndHashCode(callSuper = true)
public class VarIdentifier extends AbstractIdentifier {
    public VarIdentifier(@NonNull String value) {
        super(value);
    }

    @Override
    public NodeCriteria getNodeCriteria() {
        return NodeCriteria.exactly(input(), value(getValue()));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
