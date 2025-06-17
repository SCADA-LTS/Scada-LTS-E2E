package org.scadalts.e2e.page.core.criterias.identifiers;

import lombok.Builder;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.xpaths.XpathAttribute;
import org.scadalts.e2e.page.core.xpaths.XpathOperation;

import static org.scadalts.e2e.page.core.xpaths.XpathExpression.xpath;
import static org.scadalts.e2e.page.core.xpaths.XpathOperation.contains;
import static org.scadalts.e2e.page.core.xpaths.XpathOperation.equal;

@Builder
public class NodeCriteriaWithNode implements NodeCriteria {

    private final Tag parent;
    private final Tag child;
    private final XpathAttribute childAttribute;
    private final XpathAttribute parentAttribute1;
    private final XpathAttribute parentAttribute2;

    private final boolean equal;

    NodeCriteriaWithNode(Tag parent, Tag child, XpathAttribute childAttribute, boolean equal) {
        this.parent = parent;
        this.child = child;
        this.childAttribute = childAttribute;
        this.parentAttribute1 = XpathAttribute.empty();
        this.parentAttribute2 = XpathAttribute.empty();
        this.equal = equal;
    }

    NodeCriteriaWithNode(Tag parent, Tag child, XpathAttribute childAttribute, XpathAttribute parentAttribute1, boolean equal) {
        this.parent = parent;
        this.child = child;
        this.childAttribute = childAttribute;
        this.parentAttribute1 = parentAttribute1;
        this.parentAttribute2 = XpathAttribute.empty();
        this.equal = equal;
    }
    NodeCriteriaWithNode(Tag parent, Tag child, XpathAttribute childAttribute, XpathAttribute parentAttribute1,
                         XpathAttribute parentAttribute2, boolean equal) {
        this.parent = parent;
        this.child = child;
        this.childAttribute = childAttribute;
        this.parentAttribute1 = parentAttribute1 == null ? XpathAttribute.empty() : parentAttribute1;
        this.parentAttribute2 = parentAttribute2 == null ? XpathAttribute.empty() : parentAttribute2;
        this.equal = equal;
    }

    @Override
    public String getXpath() {
        boolean isTextChildAttribute = childAttribute.isText();
        boolean isTextParentAttribute1 = parentAttribute1.isText();
        boolean isTextParentAttribute2 = parentAttribute2.isText();

        XpathOperation childAttributeOperation = equal && isTextChildAttribute ? equal(childAttribute) : contains(childAttribute);
        XpathOperation parentAttribute1Operation = equal && isTextParentAttribute1 ? equal(parentAttribute1) : contains(parentAttribute1);
        XpathOperation parentAttribute2Operation = equal && isTextParentAttribute2 ? equal(parentAttribute2) : contains(parentAttribute2);
        if(parentAttribute1 != XpathAttribute.empty() || parentAttribute2 != XpathAttribute.empty()) {
            if(parentAttribute1 != XpathAttribute.empty() && parentAttribute2 != XpathAttribute.empty())
                return xpath(parent, parentAttribute1Operation.and(parentAttribute2Operation)
                        .and(xpath(child, childAttributeOperation))).expression();
            if(parentAttribute1 != XpathAttribute.empty())
                return xpath(parent, parentAttribute1Operation.and(xpath(child, childAttributeOperation))).expression();
            return xpath(parent, parentAttribute2Operation.and(xpath(child, childAttributeOperation))).expression();
        }
        return xpath(parent, xpath(child, childAttributeOperation)).expression();
    }
}
