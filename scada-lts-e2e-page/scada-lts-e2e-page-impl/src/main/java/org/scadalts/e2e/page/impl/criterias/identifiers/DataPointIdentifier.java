package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.AbstractIdentifier;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.impl.dicts.DataPointType;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DataPointIdentifier extends AbstractIdentifier {

    private final DataPointType type;

    public final static DataPointIdentifier EMPTY = new DataPointIdentifier("", DataPointType.NONE);

    public DataPointIdentifier(@NonNull String value, DataPointType type) {
        super(value);
        this.type = type;
    }

    public static DataPointIdentifier empty() {
        return EMPTY;
    }

    @Override
    public NodeCriteria getNodeCriteria() {
        return NodeCriteria.exactly(this, Tag.tr());
    }
}
