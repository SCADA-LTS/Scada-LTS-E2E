package org.scadalts.e2e.page.core.criterias.identifiers;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NodeIdentifier extends AbstractIdentifier {
    public NodeIdentifier(@NonNull String value) {
        super(value);
    }
}
