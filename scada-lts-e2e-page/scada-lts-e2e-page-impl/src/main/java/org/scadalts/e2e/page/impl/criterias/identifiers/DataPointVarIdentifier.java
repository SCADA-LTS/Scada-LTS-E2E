package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.identifiers.AbstractIdentifier;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;

import static org.scadalts.e2e.page.core.criterias.Tag.tr;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.clazz;

@Getter
@ToString(callSuper = true)
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
        return NodeCriteria.exactly(dataPointIdentifier, varIdentifier, tr(), clazz("smRow"));
    }
}
