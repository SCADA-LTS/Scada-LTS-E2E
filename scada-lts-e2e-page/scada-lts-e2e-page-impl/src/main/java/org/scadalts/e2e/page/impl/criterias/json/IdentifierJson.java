package org.scadalts.e2e.page.impl.criterias.json;

import lombok.Builder;
import org.scadalts.e2e.common.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;

@Builder
public class IdentifierJson<T extends DictionaryObject> {

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
