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
@EqualsAndHashCode(callSuper = true)
public class DataSourceIdentifier extends AbstractIdentifier {

    private final @NonNull DataSourceType type;
    public final static DataSourceIdentifier EMPTY = new DataSourceIdentifier("", DataSourceType.NONE);

    public DataSourceIdentifier(@NonNull String value, @NonNull DataSourceType type) {
        super(value);
        this.type = type;
    }

    public static DataSourceIdentifier empty() {
        return EMPTY;
    }

    @Override
    public NodeCriteria getNodeCriteria() {
        return NodeCriteria.exactly(this, tr(), clazz("row"));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}