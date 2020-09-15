package org.scadalts.e2e.page.core.criterias.identifiers;

import lombok.Builder;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.xpaths.XpathAttribute;
import org.scadalts.e2e.page.core.xpaths.XpathOperation;

import static org.scadalts.e2e.page.core.criterias.Tag.each;
import static org.scadalts.e2e.page.core.xpaths.XpathExpression.xpath;
import static org.scadalts.e2e.page.core.xpaths.XpathOperation.contains;
import static org.scadalts.e2e.page.core.xpaths.XpathOperation.equal;
import static org.scadalts.e2e.page.core.xpaths.XpathOperation.positionMod;

@Builder
class NodeCriteriaEvery implements NodeCriteria {

    private final int sectionSize;
    private final int everyoneInPosition;
    private final Tag tag;
    private final XpathAttribute attribute;
    private final XpathAttribute parentAttribute;

    NodeCriteriaEvery(int sectionSize, int everyoneInPosition, Tag tag, XpathAttribute attribute, XpathAttribute parentAttribute) {
        this.sectionSize = sectionSize;
        this.everyoneInPosition = everyoneInPosition;
        this.tag = tag;
        this.attribute = attribute;
        this.parentAttribute = parentAttribute;
    }

    @Override
    public String getXpath() {
        XpathOperation xpathOperation = positionMod(sectionSize, everyoneInPosition);
        if(!attribute.isEmpty())
            xpathOperation = xpathOperation.and(equal(attribute));
        if(parentAttribute.isEmpty())
            return xpath(tag, xpathOperation).expression();
        return xpath(each(), contains(parentAttribute))
                .add(xpath(tag, xpathOperation))
                .expression();
    }
}
