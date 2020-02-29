package org.scadalts.e2e.page.impl.criterias;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.scadalts.e2e.common.dicts.E2eDictionary;
import org.scadalts.e2e.common.dicts.EmptyType;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.utils.XpathFactory;

@Getter
@ToString
@EqualsAndHashCode
public class GraphicalViewCriteria implements CriteriaObject {

    @Getter
    private final GraphicalViewIdentifier identifier;

    public GraphicalViewCriteria(GraphicalViewIdentifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public E2eDictionary getType() {
        return EmptyType.ANY;
    }

    @Override
    public String getXpath() {
        return XpathFactory.xpath("option", identifier.getValue());
    }
}
