package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.identifiers.AbstractIdentifier;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;

import static org.scadalts.e2e.page.core.criterias.Tag.tr;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.clazz;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DataSourceIdentifier extends AbstractIdentifier {

    private final @NonNull DataSourceType type;

    public DataSourceIdentifier(@NonNull String value, @NonNull DataSourceType type) {
        super(value);
        this.type = type;
    }

    @Override
    public NodeCriteria getNodeCriteria() {
        return NodeCriteria.exactly(this, tr(), clazz("row"));
    }
}