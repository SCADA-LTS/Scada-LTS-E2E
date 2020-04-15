package org.scadalts.e2e.page.impl.criterias.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.scadalts.e2e.common.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;

import java.util.Objects;

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSourceCriteriaJson implements CriteriaObject {

    private IdentifierJson identifier;
    private String type;
    private boolean enabled;

    public IdentifierObject getIdentifier() {
        return identifier;
    }

    public DictionaryObject getType() {
        return DataSourceType.valueOf(type);
    }

    public DataSourceCriteria toCriteria() {
        return DataSourceCriteria.criteriaSecond(new DataSourceIdentifier(identifier.getValue(),DataSourceType.valueOf(type)),
                enabled);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSourceCriteriaJson)) return false;
        DataSourceCriteriaJson that = (DataSourceCriteriaJson) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}
