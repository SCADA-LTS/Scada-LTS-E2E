package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.identifiers.AbstractIdentifier;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.impl.dicts.EventType;

import static org.scadalts.e2e.page.core.criterias.Tag.td;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.clazz;

@Getter
@Builder
@EqualsAndHashCode(callSuper = true)
public class PointLinkIdentifier extends AbstractIdentifier {

    private final @NonNull DataSourcePointIdentifier source;
    private final @NonNull DataSourcePointIdentifier target;
    private final @NonNull EventType type;

    public PointLinkIdentifier(@NonNull DataSourcePointIdentifier source,
                               @NonNull DataSourcePointIdentifier target, @NonNull EventType type) {
        super(source.getValue() + " " + target.getValue());
        this.source = source;
        this.target = target;
        this.type = type;
    }

    @Override
    public NodeCriteria getNodeCriteria() {
        return NodeCriteria.exactly(source, target, td(), clazz("link"));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}