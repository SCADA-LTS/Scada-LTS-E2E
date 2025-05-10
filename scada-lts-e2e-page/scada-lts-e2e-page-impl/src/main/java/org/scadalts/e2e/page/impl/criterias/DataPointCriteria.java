package org.scadalts.e2e.page.impl.criterias;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointLoggingProperties;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.ChangeType;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.LoggingType;

import java.util.Objects;

@Data
@ToString
public class DataPointCriteria implements CriteriaObject, GetXid {

    private final @NonNull Xid xid;
    private final @NonNull DataPointIdentifier identifier;
    private final boolean settable;
    private final boolean enabled;
    private final @NonNull DataPointProperties dataPointProperties;

    static DataPointCriteria EMPTY = new DataPointCriteria();

    static DataPointCriteria empty() {
        return EMPTY;
    }

    protected DataPointCriteria() {
        this.xid = new Xid("");
        this.identifier = new DataPointIdentifier("", DataPointType.NONE);
        this.settable = false;
        this.enabled = false;
        this.dataPointProperties = DataPointProperties.empty();
    }

    protected DataPointCriteria(@NonNull Xid xid, @NonNull DataPointIdentifier identifier, boolean settable, boolean enabled,
                                @NonNull DataPointProperties dataPointProperties) {
        this.xid = xid;
        this.identifier = identifier;
        this.settable = settable;
        this.enabled = enabled;
        this.dataPointProperties = dataPointProperties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataPointCriteria)) return false;
        DataPointCriteria that = (DataPointCriteria) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}
