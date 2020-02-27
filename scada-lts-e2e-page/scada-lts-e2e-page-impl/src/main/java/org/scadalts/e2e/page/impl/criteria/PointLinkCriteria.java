package org.scadalts.e2e.page.impl.criteria;

import lombok.Builder;
import lombok.Getter;
import org.scadalts.e2e.page.core.criteria.ObjectCriteria;
import org.scadalts.e2e.page.core.util.XpathFactory;
import org.scadalts.e2e.page.impl.dict.EventType;

@Getter
@Builder
public class PointLinkCriteria implements ObjectCriteria {

    private final SourcePointCriteria source;
    private final SourcePointCriteria target;
    private final EventType type;
    private final String script;

    public static PointLinkCriteria change() {
        return PointLinkCriteria.builder()
                .type(EventType.CHANGE)
                .script("return source.value;")
                .source(SourcePointCriteria.virtualDataSourceBinaryAlternate())
                .target(SourcePointCriteria.virtualDataSourceBinaryAlternate())
                .build();
    }

    public static PointLinkCriteria criteria(EventType eventType, String script) {
        return PointLinkCriteria.builder()
                .type(eventType)
                .script(script)
                .source(SourcePointCriteria.virtualDataSourceBinaryAlternate())
                .target(SourcePointCriteria.virtualDataSourceBinaryAlternate())
                .build();
    }

    @Override
    public String getIdentifier() {
        return source.getIdentifier() + " " + target.getIdentifier();
    }

    @Override
    public String getXpath() {
        return XpathFactory.xpath("tbody", source.getIdentifier(), target.getIdentifier());
    }
}
