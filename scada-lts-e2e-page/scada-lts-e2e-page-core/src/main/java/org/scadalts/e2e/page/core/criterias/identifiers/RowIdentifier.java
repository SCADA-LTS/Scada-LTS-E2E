package org.scadalts.e2e.page.core.criterias.identifiers;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode
public class RowIdentifier implements IdentifierObject {
    private final @NonNull String value;
}
