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
public class InternalVirtualPointLinkCriteria extends PointLinkCriteria<InternalDataSourcePointCriteria, VirtualDataSourcePointCriteria> {

    @Builder
    public InternalVirtualPointLinkCriteria(@NonNull Xid xid, @NonNull EventType type, @NonNull Script script,
                                            @NonNull InternalDataSourcePointCriteria source, @NonNull VirtualDataSourcePointCriteria target) {
        super(xid, type, script, source, target);
    }

    public static InternalVirtualPointLinkCriteria change(Script script) {
        return InternalVirtualPointLinkCriteria.builder()
                .type(EventType.CHANGE)
                .script(script)
                .source(InternalDataSourcePointCriteria.criteria(UpdateDataSourceCriteria.virtualDataSourceSecond(), InternalDataPointCriteria.activeThreadCount()))
                .target(VirtualDataSourcePointCriteria.virtualDataSourceNumericNoChange())
                .xid(Xid.pointLink())
                .build();
    }

    public static InternalVirtualPointLinkCriteria change(InternalDataSourcePointCriteria source, VirtualDataSourcePointCriteria target, Script script) {
        return InternalVirtualPointLinkCriteria.builder()
                .type(EventType.CHANGE)
                .script(script)
                .source(source)
                .target(target)
                .xid(Xid.pointLink())
                .build();
    }

    public static InternalVirtualPointLinkCriteria update(Script script) {
        return InternalVirtualPointLinkCriteria.builder()
                .type(EventType.UPDATE)
                .script(script)
                .source(InternalDataSourcePointCriteria.criteria(UpdateDataSourceCriteria.virtualDataSourceSecond(), InternalDataPointCriteria.activeThreadCount()))
                .target(VirtualDataSourcePointCriteria.virtualDataSourceNumericNoChange())
                .xid(Xid.pointLink())
                .build();
    }

    public static InternalVirtualPointLinkCriteria update(InternalDataSourcePointCriteria source, VirtualDataSourcePointCriteria target,
                                                          Script script) {
        return InternalVirtualPointLinkCriteria.builder()
                .type(EventType.UPDATE)
                .script(script)
                .source(source)
                .target(target)
                .xid(Xid.pointLink())
                .build();
    }

    public static InternalVirtualPointLinkCriteria criteria(InternalDataSourcePointCriteria source, VirtualDataSourcePointCriteria target,
                                                            EventType eventType, Script script) {
        return InternalVirtualPointLinkCriteria.builder()
                .type(eventType)
                .script(script)
                .source(source)
                .target(target)
                .xid(Xid.pointLink())
                .build();
    }

    public static InternalVirtualPointLinkCriteria criteria(EventType eventType, Script script) {
        return InternalVirtualPointLinkCriteria.builder()
                .type(eventType)
                .script(script)
                .source(InternalDataSourcePointCriteria.criteria(UpdateDataSourceCriteria.virtualDataSourceSecond(), InternalDataPointCriteria.activeThreadCount()))
                .target(VirtualDataSourcePointCriteria.virtualDataSourceNumericNoChange())
                .xid(Xid.pointLink())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InternalVirtualPointLinkCriteria)) return false;
        InternalVirtualPointLinkCriteria that = (InternalVirtualPointLinkCriteria) o;
        return Objects.equals(getSource(), that.getSource()) &&
                Objects.equals(getTarget(), that.getTarget());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSource(), getTarget());
    }
}
