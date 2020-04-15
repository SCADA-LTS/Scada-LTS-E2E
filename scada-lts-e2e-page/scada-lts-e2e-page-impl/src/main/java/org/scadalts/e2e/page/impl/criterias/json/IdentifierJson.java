package org.scadalts.e2e.page.impl.criterias.json;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;

@Data
@NoArgsConstructor
public class IdentifierJson implements IdentifierObject {

    private String value;
}
