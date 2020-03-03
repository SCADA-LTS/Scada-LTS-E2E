package org.scadalts.e2e.page.core.criterias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;

@Data
@ToString
@EqualsAndHashCode
public class CssClass implements IdentifierObject {
    private final String value;

    private final static CssClass EMPTY = new CssClass("");

    public static CssClass empty() {
        return EMPTY;
    }
}
