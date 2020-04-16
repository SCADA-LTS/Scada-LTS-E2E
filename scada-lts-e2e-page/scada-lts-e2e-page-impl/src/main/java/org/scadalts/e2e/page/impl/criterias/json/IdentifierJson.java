package org.scadalts.e2e.page.impl.criterias.json;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;

@Data
@NoArgsConstructor
public class IdentifierJson implements IdentifierObject {

    IdentifierObject identifier;

    public IdentifierJson(IdentifierObject identifier) {
        this.identifier = identifier;
    }

    public String getValue() {
        return identifier.getValue();
    }

    public T getType() {
        return (T)identifier.getType();
    }

    public static IdentifierObject empty() {
        return IdentifierObject.empty();
    }
}
