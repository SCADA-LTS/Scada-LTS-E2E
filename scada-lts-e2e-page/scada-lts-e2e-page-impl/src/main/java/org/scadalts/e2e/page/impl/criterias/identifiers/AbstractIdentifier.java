package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;

@Data
@EqualsAndHashCode
class AbstractIdentifier implements IdentifierObject {
    private final @NonNull String value;

    @Override
    public String toString() {
        return value;
    }
}
