package org.scadalts.e2e.page.core.criterias;

import lombok.Builder;
import org.scadalts.e2e.common.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.utils.XpathFactory;

@Builder
class NodeCriteriaEvery implements NodeCriteria {

    private final int sectionSize;
    private final int everyoneInPosition;
    private final Tag tag;
    private final CssClass cssClass;
    private final CssClass cssClassParent;

    public NodeCriteriaEvery(int sectionSize, int everyoneInPosition, Tag tag, CssClass cssClass, CssClass cssClassParent) {
        this.sectionSize = sectionSize;
        this.everyoneInPosition = everyoneInPosition;
        this.tag = tag;
        this.cssClass = cssClass;
        this.cssClassParent = cssClassParent;
    }

    @Override
    public String getXpath() {
        if(cssClass == null || CssClass.empty().equals(cssClass)) {
            if(cssClassParent == null || CssClass.empty().equals(cssClassParent)) {
                return XpathFactory.xpathEveryXElement(sectionSize, everyoneInPosition, tag);
            }
            return XpathFactory.xpathEveryXElementInParent(sectionSize, everyoneInPosition, tag, cssClassParent);
        }
        return XpathFactory.xpathEveryXElement(sectionSize, everyoneInPosition, tag, cssClass);
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
