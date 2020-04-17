package org.scadalts.e2e.page.impl.criterias.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;

import java.text.MessageFormat;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSourceCriteriaJson {

    private IdentifierJson<DataSourceType> identifier;
    private boolean enabled;

    private DataSourceCriteriaJson(IdentifierJson<DataSourceType> identifier, boolean enabled) {
        this.identifier = identifier;
        this.enabled = enabled;
    }

    public DataSourceCriteria toDataSourceSecondCriteria() {
        return DataSourceCriteria.criteriaSecond(new DataSourceIdentifier(identifier.getValue(),identifier.getType()),
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

    @Override
    public String toString() {
        return MessageFormat.format("name: {0}, type: {1}, enabled: {2}", identifier.getValue(),
                identifier.getType(), enabled);
    }
}
