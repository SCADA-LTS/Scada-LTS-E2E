package org.scadalts.e2e.page.impl.criterias.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentifierJson<T extends DictionaryObject> {

    private String value;
    private T type;

    public IdentifierJson(IdentifierObject identifier) {
        this.value = identifier.getValue();
        this.type = (T)identifier.getType();
    }

    public static IdentifierObject empty() {
        return IdentifierObject.empty();
    }
}
