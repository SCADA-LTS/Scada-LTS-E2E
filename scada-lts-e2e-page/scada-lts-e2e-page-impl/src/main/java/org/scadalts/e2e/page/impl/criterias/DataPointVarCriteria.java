package org.scadalts.e2e.page.impl.criterias;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointVarIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.VarIdentifier;

import java.util.Objects;

@Data
@ToString
public class DataPointVarCriteria implements CriteriaObject {

    private final @NonNull DataSourcePointIdentifier dataSourcePointIdentifier;
    private final @NonNull VarCriteria varCriteria;

    public static DataPointVarCriteria criteria(@NonNull DataSourcePointIdentifier dataSourcePointIdentifier) {
        return new DataPointVarCriteria(dataSourcePointIdentifier, new VarCriteria(new VarIdentifier("abc")));
    }

    @Override
    public IdentifierObject getIdentifier() {
        return new DataSourcePointVarIdentifier(dataSourcePointIdentifier, varCriteria.getIdentifier());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataPointVarCriteria)) return false;
        DataPointVarCriteria that = (DataPointVarCriteria) o;
        return Objects.equals(getDataSourcePointIdentifier(), that.getDataSourcePointIdentifier()) &&
                Objects.equals(getVarCriteria(), that.getVarCriteria());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getDataSourcePointIdentifier(), getVarCriteria());
    }
}
