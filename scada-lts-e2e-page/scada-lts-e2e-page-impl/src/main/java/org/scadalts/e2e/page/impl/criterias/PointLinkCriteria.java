package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.PointLinkIdentifier;
import org.scadalts.e2e.page.impl.dicts.EventType;

import java.util.Objects;

@Data
@Builder
@ToString
@EqualsAndHashCode
public class PointLinkCriteria implements CriteriaObject, GetXid {

    @Deprecated
    private final @NonNull Xid xid;
    private final @NonNull @Getter DataSourcePointCriteria source;
    private final @NonNull @Getter DataSourcePointCriteria target;
    private final @NonNull EventType type;
    private final @NonNull String script;

    public static PointLinkCriteria change() {
        return PointLinkCriteria.builder()
                .type(EventType.CHANGE)
                .script("return source.value;")
                .source(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .target(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .xid(Xid.xidForPointLink())
                .build();
    }

    public static PointLinkCriteria change(DataSourcePointCriteria source, DataSourcePointCriteria target) {
        return PointLinkCriteria.builder()
                .type(EventType.CHANGE)
                .script("return source.value;")
                .source(source)
                .target(target)
                .xid(Xid.xidForPointLink())
                .build();
    }

    public static PointLinkCriteria update() {
        return PointLinkCriteria.builder()
                .type(EventType.UPDATE)
                .script("return source.value;")
                .source(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .target(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .xid(Xid.xidForPointLink())
                .build();
    }

    public static PointLinkCriteria update(DataSourcePointCriteria source, DataSourcePointCriteria target) {
        return PointLinkCriteria.builder()
                .type(EventType.UPDATE)
                .script("return source.value;")
                .source(source)
                .target(target)
                .xid(Xid.xidForPointLink())
                .build();
    }

    public static PointLinkCriteria criteria(DataSourcePointCriteria source, DataSourcePointCriteria target, EventType eventType) {
        return PointLinkCriteria.builder()
                .type(eventType)
                .script("return source.value;")
                .source(source)
                .target(target)
                .xid(Xid.xidForPointLink())
                .build();
    }

    public static PointLinkCriteria criteria(EventType eventType, String script) {
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
        return new PointLinkIdentifier(source.getIdentifier().getValue() + " " + target.getIdentifier().getValue());
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
