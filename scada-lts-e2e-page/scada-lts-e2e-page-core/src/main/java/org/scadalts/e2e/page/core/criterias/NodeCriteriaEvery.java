package org.scadalts.e2e.page.core.criterias;

import org.scadalts.e2e.common.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.utils.XpathFactory;

class NodeCriteriaEvery implements NodeCriteria {

    private final int every;
    private final Tag tag;

    public NodeCriteriaEvery(int every, Tag tag) {
        this.every = every;
        this.tag = tag;
    }

    @Override
    public String getXpath() {
        return XpathFactory.xpathEveryXElementFirst(every, tag);
    }

    @Override
    public IdentifierObject getIdentifier() {
        return IdentifierObject.empty();
    }

    @Override
    public DictionaryObject getType() {
        return DictionaryObject.any();
    }
}
