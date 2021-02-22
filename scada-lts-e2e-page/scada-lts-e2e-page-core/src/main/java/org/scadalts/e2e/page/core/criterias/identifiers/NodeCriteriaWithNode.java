package org.scadalts.e2e.page.core.criterias.identifiers;

import lombok.Builder;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.xpaths.XpathAttribute;

import static org.scadalts.e2e.page.core.xpaths.XpathExpression.xpath;
import static org.scadalts.e2e.page.core.xpaths.XpathOperation.contains;

@Builder
public class NodeCriteriaWithNode implements NodeCriteria {

    private final Tag parent;
    private final Tag kid;
    private final XpathAttribute kidAttribute;

    NodeCriteriaWithNode(Tag parent, Tag kid, XpathAttribute kidAttribute) {
        this.parent = parent;
        this.kid = kid;
        this.kidAttribute = kidAttribute;
    }

    @Override
    public String getXpath() {
        return xpath(parent, xpath(kid, contains(kidAttribute))).expression();
    }
}
