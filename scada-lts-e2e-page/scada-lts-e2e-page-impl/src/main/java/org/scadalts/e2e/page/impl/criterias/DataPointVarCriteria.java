package org.scadalts.e2e.page.impl.criterias;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointVarIdentifier;

import java.util.Objects;

@Data
@ToString
public class DataPointVarCriteria implements CriteriaObject {

    private final @NonNull DataPointCriteria dataPointCriteria;
    private final @NonNull VarCriteria varCriteria;

    public static DataPointVarCriteria criteria(@NonNull DataPointCriteria criteria) {
        return new DataPointVarCriteria(criteria, VarCriteria.empty());
    }

    @Override
    public IdentifierObject getIdentifier() {
        return new DataPointVarIdentifier(dataPointCriteria.getIdentifier(), varCriteria.getIdentifier());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataPointVarCriteria)) return false;
        DataPointVarCriteria that = (DataPointVarCriteria) o;
        return Objects.equals(getDataPointCriteria(), that.getDataPointCriteria()) &&
                Objects.equals(getVarCriteria(), that.getVarCriteria());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getDataPointCriteria(), getVarCriteria());
    }
}
