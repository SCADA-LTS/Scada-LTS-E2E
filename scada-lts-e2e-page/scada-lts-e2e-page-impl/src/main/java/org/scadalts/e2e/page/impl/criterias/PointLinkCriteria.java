package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.impl.criterias.identifiers.PointLinkIdentifier;
import org.scadalts.e2e.page.impl.dicts.EventType;

import java.util.Objects;

@ToString
@Getter
public abstract class PointLinkCriteria<R extends DataSourcePointCriteria<?,?>, W extends DataSourcePointCriteria<?,?>> implements CriteriaObject, GetXid {

    private final @NonNull Xid xid;
    private final @NonNull EventType type;
    private final @NonNull Script script;
    private final @NonNull R source;
    private final @NonNull W target;

    public PointLinkCriteria(@NonNull Xid xid, @NonNull EventType type, @NonNull Script script, @NonNull R source, @NonNull W target) {
        this.xid = xid;
        this.type = type;
        this.script = script;
        this.source = source;
        this.target = target;
    }

    @Override
    public PointLinkIdentifier getIdentifier() {
        return PointLinkIdentifier.builder()
                .source(getSource().getIdentifier())
                .target(getTarget().getIdentifier())
                .type(getType())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointLinkCriteria)) return false;
        PointLinkCriteria<?, ?> that = (PointLinkCriteria<?, ?>) o;
        return Objects.equals(getSource(), that.getSource()) &&
                Objects.equals(getTarget(), that.getTarget());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSource(), getTarget());
    }
}
