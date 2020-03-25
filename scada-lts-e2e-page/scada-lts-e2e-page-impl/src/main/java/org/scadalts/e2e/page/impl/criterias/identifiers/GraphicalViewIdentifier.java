package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.identifiers.AbstractIdentifier;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GraphicalViewIdentifier extends AbstractIdentifier {

    public GraphicalViewIdentifier(@NonNull String value) {
        super(value);
    }
}
