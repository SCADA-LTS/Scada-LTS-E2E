package org.scadalts.e2e.page.core.criterias.identifiers;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public abstract class AbstractIdentifier implements IdentifierObject {

    private final @NonNull String value;

    @Override
    public String toString() {
        return value;
    }

}
