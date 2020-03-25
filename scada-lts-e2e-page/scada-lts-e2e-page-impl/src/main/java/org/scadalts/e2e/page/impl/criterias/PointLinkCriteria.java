package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.impl.criterias.identifiers.PointLinkIdentifier;
import org.scadalts.e2e.page.impl.dicts.EventType;

import java.util.Objects;

@Builder
@ToString
public class PointLinkCriteria implements CriteriaObject, GetXid {

    private final @NonNull @Getter Xid xid;
    private final @NonNull @Getter DataSourcePointCriteria source;
    private final @NonNull @Getter DataSourcePointCriteria target;
    private final @NonNull EventType type;
    private final @NonNull @Getter Script script;

    public static PointLinkCriteria change(Script script) {
        return PointLinkCriteria.builder()
                .type(EventType.CHANGE)
                .script(script)
                .source(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .target(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .xid(Xid.xidForPointLink())
                .build();
    }

    public static PointLinkCriteria change(DataSourcePointCriteria source, DataSourcePointCriteria target, Script script) {
        return PointLinkCriteria.builder()
                .type(EventType.CHANGE)
                .script(script)
                .source(source)
                .target(target)
                .xid(Xid.xidForPointLink())
                .build();
    }

    public static PointLinkCriteria update(Script script) {
        return PointLinkCriteria.builder()
                .type(EventType.UPDATE)
                .script(script)
                .source(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .target(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .xid(Xid.xidForPointLink())
                .build();
    }

    public static PointLinkCriteria update(DataSourcePointCriteria source, DataSourcePointCriteria target,
                                           Script script) {
        return PointLinkCriteria.builder()
                .type(EventType.UPDATE)
                .script(script)
                .source(source)
                .target(target)
                .xid(Xid.xidForPointLink())
                .build();
    }

    public static PointLinkCriteria criteria(DataSourcePointCriteria source, DataSourcePointCriteria target,
                                             EventType eventType, Script script) {
        return PointLinkCriteria.builder()
                .type(eventType)
                .script(script)
                .source(source)
                .target(target)
                .xid(Xid.xidForPointLink())
                .build();
    }

    public static PointLinkCriteria criteria(EventType eventType, Script script) {
        return PointLinkCriteria.builder()
                .type(eventType)
                .script(script)
                .source(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .target(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .xid(Xid.xidForPointLink())
                .build();
    }

    @Override
    public PointLinkIdentifier getIdentifier() {
        return PointLinkIdentifier.builder()
        .source(source.getIdentifier())
        .target(target.getIdentifier())
        .type(type)
        .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointLinkCriteria)) return false;
        PointLinkCriteria that = (PointLinkCriteria) o;
        return Objects.equals(getSource(), that.getSource()) &&
                Objects.equals(getTarget(), that.getTarget());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSource(), getTarget());
    }
}
