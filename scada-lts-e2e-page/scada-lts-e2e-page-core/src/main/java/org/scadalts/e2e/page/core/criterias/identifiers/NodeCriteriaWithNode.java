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
    private final XpathAttribute parentAttribute1;
    private final XpathAttribute parentAttribute2;

    NodeCriteriaWithNode(Tag parent, Tag child, XpathAttribute childAttribute) {
        this.parent = parent;
        this.child = child;
        this.childAttribute = childAttribute;
        this.parentAttribute1 = XpathAttribute.empty();
        this.parentAttribute2 = XpathAttribute.empty();
    }

    NodeCriteriaWithNode(Tag parent, Tag child, XpathAttribute childAttribute, XpathAttribute parentAttribute1) {
        this.parent = parent;
        this.child = child;
        this.childAttribute = childAttribute;
        this.parentAttribute1 = parentAttribute1;
        this.parentAttribute2 = XpathAttribute.empty();
    }
    NodeCriteriaWithNode(Tag parent, Tag child, XpathAttribute childAttribute, XpathAttribute parentAttribute1,
                         XpathAttribute parentAttribute2) {
        this.parent = parent;
        this.child = child;
        this.childAttribute = childAttribute;
        this.parentAttribute1 = parentAttribute1 == null ? XpathAttribute.empty() : parentAttribute1;
        this.parentAttribute2 = parentAttribute2 == null ? XpathAttribute.empty() : parentAttribute2;
    }

    @Override
    public String getXpath() {
        if(parentAttribute1 != XpathAttribute.empty() || parentAttribute2 != XpathAttribute.empty()) {
            if(parentAttribute1 != XpathAttribute.empty() && parentAttribute2 != XpathAttribute.empty())
                return xpath(parent, contains(parentAttribute1).and(contains(parentAttribute2))
                        .and(xpath(child, contains(childAttribute)))).expression();
            if(parentAttribute1 != XpathAttribute.empty())
                return xpath(parent, contains(parentAttribute1).and(xpath(child, contains(childAttribute)))).expression();
            return xpath(parent, contains(parentAttribute2).and(xpath(child, contains(childAttribute)))).expression();
        }
        return xpath(parent, xpath(child, contains(childAttribute))).expression();
    }
}
