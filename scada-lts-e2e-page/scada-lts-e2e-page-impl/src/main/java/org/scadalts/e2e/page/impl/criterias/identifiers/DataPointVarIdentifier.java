package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.scadalts.e2e.page.core.criterias.identifiers.AbstractIdentifier;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;

import static org.scadalts.e2e.page.core.criterias.Tag.*;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.*;

@Getter
@EqualsAndHashCode(callSuper = true)
public class DataPointVarIdentifier extends AbstractIdentifier {

    private final @NonNull DataPointIdentifier dataPointIdentifier;
    private final @NonNull VarIdentifier varIdentifier;

    public DataPointVarIdentifier(@NonNull DataPointIdentifier dataPointIdentifier, @NonNull VarIdentifier varIdentifier) {
        super(dataPointIdentifier.getValue() + " - " + varIdentifier.getValue());
        this.dataPointIdentifier = dataPointIdentifier;
        this.varIdentifier = varIdentifier;
    }

    @Override
    public NodeCriteria getNodeCriteria() {
        return NodeCriteria.withNode(tr(), input(), value(varIdentifier.getValue()), clazz("smRow"),
                text(dataPointIdentifier.getValue()));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
