package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.AbstractIdentifier;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;

import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.clazz;

@EqualsAndHashCode(callSuper = true)
public class UserIdentifier extends AbstractIdentifier {

    public UserIdentifier(@NonNull String value) {
        super(value);
    }

    @Override
    public NodeCriteria getNodeCriteria() {
        return NodeCriteria.textEqual(this, Tag.td(), clazz("link"));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
