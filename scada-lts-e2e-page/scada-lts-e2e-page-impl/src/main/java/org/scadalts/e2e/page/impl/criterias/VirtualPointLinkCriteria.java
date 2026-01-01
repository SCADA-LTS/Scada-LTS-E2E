package org.scadalts.e2e.page.impl.criterias;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.impl.dicts.EventType;

import java.util.Objects;

@ToString
@Getter
public class VirtualPointLinkCriteria extends PointLinkCriteria<VirtualDataSourcePointCriteria, VirtualDataSourcePointCriteria> {

    @Builder
    public VirtualPointLinkCriteria(@NonNull Xid xid, @NonNull EventType type, @NonNull Script script,
                                    @NonNull VirtualDataSourcePointCriteria source, @NonNull VirtualDataSourcePointCriteria target) {
        super(xid, type, script, source, target);
    }

    public static VirtualPointLinkCriteria change(Script script) {
        return VirtualPointLinkCriteria.builder()
                .type(EventType.CHANGE)
                .script(script)
                .source(VirtualDataSourcePointCriteria.virtualDataSourceNumericNoChange())
                .target(VirtualDataSourcePointCriteria.virtualDataSourceNumericNoChange())
                .xid(Xid.pointLink())
                .build();
    }

    public static VirtualPointLinkCriteria change(VirtualDataSourcePointCriteria source, VirtualDataSourcePointCriteria target, Script script) {
        return VirtualPointLinkCriteria.builder()
                .type(EventType.CHANGE)
                .script(script)
                .source(source)
                .target(target)
                .xid(Xid.pointLink())
                .build();
    }

    public static VirtualPointLinkCriteria update(Script script) {
        return VirtualPointLinkCriteria.builder()
                .type(EventType.UPDATE)
                .script(script)
                .source(VirtualDataSourcePointCriteria.virtualDataSourceNumericNoChange())
                .target(VirtualDataSourcePointCriteria.virtualDataSourceNumericNoChange())
                .xid(Xid.pointLink())
                .build();
    }

    public static VirtualPointLinkCriteria update(VirtualDataSourcePointCriteria source, VirtualDataSourcePointCriteria target,
                                                  Script script) {
        return VirtualPointLinkCriteria.builder()
                .type(EventType.UPDATE)
                .script(script)
                .source(source)
                .target(target)
                .xid(Xid.pointLink())
                .build();
    }

    public static VirtualPointLinkCriteria criteria(VirtualDataSourcePointCriteria source, VirtualDataSourcePointCriteria target,
                                                    EventType eventType, Script script) {
        return VirtualPointLinkCriteria.builder()
                .type(eventType)
                .script(script)
                .source(source)
                .target(target)
                .xid(Xid.pointLink())
                .build();
    }

    public static VirtualPointLinkCriteria criteria(EventType eventType, Script script) {
        return VirtualPointLinkCriteria.builder()
                .type(eventType)
                .script(script)
                .source(VirtualDataSourcePointCriteria.virtualDataSourceNumericNoChange())
                .target(VirtualDataSourcePointCriteria.virtualDataSourceNumericNoChange())
                .xid(Xid.pointLink())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VirtualPointLinkCriteria)) return false;
        VirtualPointLinkCriteria that = (VirtualPointLinkCriteria) o;
        return Objects.equals(getSource(), that.getSource()) &&
                Objects.equals(getTarget(), that.getTarget());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSource(), getTarget());
    }
}
