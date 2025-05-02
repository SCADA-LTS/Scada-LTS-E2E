package org.scadalts.e2e.page.impl.criterias;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;

import java.util.Objects;


@Data
public class DataSourceCriteria implements CriteriaObject, GetXid {

    private final @NonNull Xid xid;
    private final @NonNull DataSourceIdentifier identifier;
    private final boolean enabled;

    static DataSourceCriteria EMPTY = new DataSourceCriteria();

    static DataSourceCriteria empty() {
        return EMPTY;
    }

    public DataSourceCriteria() {
        this.xid = new Xid("");
        this.identifier = new DataSourceIdentifier("", DataSourceType.NONE);
        this.enabled = false;
    }

    public DataSourceCriteria(@NonNull Xid xid, @NonNull DataSourceIdentifier identifier, boolean enabled) {
        this.xid = xid;
        this.identifier = identifier;
        this.enabled = enabled;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSourceCriteria)) return false;
        DataSourceCriteria that = (DataSourceCriteria) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}
