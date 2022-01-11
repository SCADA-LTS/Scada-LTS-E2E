package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;

import java.util.Objects;

@Data
@Builder
@ToString
public class GraphicalViewCriteria implements CriteriaObject, GetXid {

    private final @NonNull Xid xid;
    private final @NonNull GraphicalViewIdentifier identifier;

    private GraphicalViewCriteria(@NonNull Xid xid, @NonNull GraphicalViewIdentifier identifier) {
        this.xid = xid;
        this.identifier = identifier;
    }

    public static GraphicalViewCriteria criteria(GraphicalViewIdentifier identifier) {
        Xid xid = Xid.graphicalView();
        return new GraphicalViewCriteria(xid, identifier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphicalViewCriteria)) return false;
        GraphicalViewCriteria that = (GraphicalViewCriteria) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}
