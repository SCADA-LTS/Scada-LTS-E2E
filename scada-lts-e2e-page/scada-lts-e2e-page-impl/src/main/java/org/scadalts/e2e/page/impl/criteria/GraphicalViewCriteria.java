package org.scadalts.e2e.page.impl.criteria;

import org.scadalts.e2e.common.dicts.E2eDictionary;
import org.scadalts.e2e.common.dicts.EmptyType;
import org.scadalts.e2e.page.core.criteria.ObjectCriteria;
import org.scadalts.e2e.page.core.util.XpathFactory;

public class GraphicalViewCriteria implements ObjectCriteria {

    private final String name;

    public GraphicalViewCriteria(String name) {
        this.name = name;
    }

    @Override
    public String getIdentifier() {
        return name;
    }

    @Override
    public E2eDictionary getType() {
        return EmptyType.ANY;
    }

    @Override
    public String getXpath() {
        return XpathFactory.xpath("option", name);
    }
}
