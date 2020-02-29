package org.scadalts.e2e.page.impl.criterias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.IdentifierObject;

@Data
@ToString
@EqualsAndHashCode
public class GraphicalViewIdentifier implements IdentifierObject {
    private final String value;
}
