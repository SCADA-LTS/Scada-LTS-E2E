package org.scadalts.e2e.page.core.criterias.identifiers;

import lombok.Builder;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.xpaths.XpathAttribute;

import static org.scadalts.e2e.page.core.xpaths.XpathExpression.xpath;
import static org.scadalts.e2e.page.core.xpaths.XpathOperation.contains;

@Builder
public class NodeCriteriaWithNode implements NodeCriteria {

    private final Tag parent;
    private final Tag child;
    private final XpathAttribute childAttribute;

    NodeCriteriaWithNode(Tag parent, Tag child, XpathAttribute childAttribute) {
        this.parent = parent;
        this.child = child;
        this.childAttribute = childAttribute;
    }

    @Override
    public String getXpath() {
        return xpath(parent, xpath(child, contains(childAttribute))).expression();
    }
}
