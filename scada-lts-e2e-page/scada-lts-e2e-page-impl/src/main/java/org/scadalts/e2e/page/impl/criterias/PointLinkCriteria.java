package org.scadalts.e2e.page.impl.criterias;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.IdentifierObject;
import org.scadalts.e2e.page.core.utils.XpathFactory;
import org.scadalts.e2e.page.impl.dicts.EventType;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class PointLinkCriteria implements CriteriaObject {

    private final DataSourcePointCriteria source;
    private final DataSourcePointCriteria target;
    private final EventType type;
    private final String script;

    public static PointLinkCriteria change() {
        return PointLinkCriteria.builder()
                .type(EventType.CHANGE)
                .script("return source.value;")
                .source(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .target(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .build();
    }

    public static PointLinkCriteria change(DataSourcePointCriteria source, DataSourcePointCriteria target) {
        return PointLinkCriteria.builder()
                .type(EventType.CHANGE)
                .script("return source.value;")
                .source(source)
                .target(target)
                .build();
    }

    public static PointLinkCriteria update() {
        return PointLinkCriteria.builder()
                .type(EventType.UPDATE)
                .script("return source.value;")
                .source(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .target(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .build();
    }

    public static PointLinkCriteria update(DataSourcePointCriteria source, DataSourcePointCriteria target) {
        return PointLinkCriteria.builder()
                .type(EventType.UPDATE)
                .script("return source.value;")
                .source(source)
                .target(target)
                .build();
    }

    public static PointLinkCriteria criteria(DataSourcePointCriteria source, DataSourcePointCriteria target, EventType eventType) {
        return PointLinkCriteria.builder()
                .type(eventType)
                .script("return source.value;")
                .source(source)
                .target(target)
                .build();
    }

    public static PointLinkCriteria criteria(EventType eventType, String script) {
        return PointLinkCriteria.builder()
                .type(eventType)
                .script(script)
                .source(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .target(DataSourcePointCriteria.virtualDataSourceBinaryAlternate())
                .build();
    }

    @Override
    public IdentifierObject getIdentifier() {
        return new PointLinkIdentifier(source.getIdentifier().getValue() + " " + target.getIdentifier().getValue());
    }

    @Override
    public String getXpath() {
        return XpathFactory.xpath("tbody", source.getIdentifier().getValue(), target.getIdentifier().getValue());
    }
}
