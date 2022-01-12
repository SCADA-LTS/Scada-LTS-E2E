package org.scadalts.e2e.page.core.criterias.identifiers;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.xpaths.XpathAttribute;
import org.scadalts.e2e.page.core.xpaths.XpathOperation;

import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.text;
import static org.scadalts.e2e.page.core.xpaths.XpathExpression.xpath;
import static org.scadalts.e2e.page.core.xpaths.XpathOperation.contains;

@Data
@ToString
@EqualsAndHashCode
class NodeCriteriaExactly implements NodeCriteria {

    private final IdentifierObject identifier1;
    private final IdentifierObject identifier2;
    private final DictionaryObject type;
    private final Tag tag;
    private final XpathAttribute xpathAttribute;

    NodeCriteriaExactly(IdentifierObject identifier1, IdentifierObject identifier2, DictionaryObject type, Tag tag, XpathAttribute xpathAttribute) {
        this.identifier1 = identifier1;
        this.identifier2 = identifier2;
        this.type = type;
        this.tag = tag;
        this.xpathAttribute = xpathAttribute;
    }

    @Override
    public String getXpath() {
        if(identifier1 != IdentifierObject.empty()) {
            XpathOperation xpathOperation = XpathOperation.contains(text(identifier1.getValue()));
            if (identifier2 != IdentifierObject.empty() && !identifier1.equals(identifier2))
                xpathOperation = xpathOperation.and(contains(text(identifier2.getValue())));
            if (type != DictionaryObject.any())
                xpathOperation = xpathOperation.and(contains(text(type.getName())));
            if (!xpathAttribute.isEmpty())
                xpathOperation = xpathOperation.and(contains(xpathAttribute));
            return xpath(tag, xpathOperation).expression();
        }
        if(identifier2 != IdentifierObject.empty()) {
            XpathOperation xpathOperation = XpathOperation.contains(text(identifier2.getValue()));
            if (type != DictionaryObject.any())
                xpathOperation = xpathOperation.and(contains(text(type.getName())));
            if (!xpathAttribute.isEmpty())
                xpathOperation = xpathOperation.and(contains(xpathAttribute));
            return xpath(tag, xpathOperation).expression();
        }
        XpathOperation xpathOperation = XpathOperation.contains(xpathAttribute) ;
        if (type != DictionaryObject.any())
            xpathOperation = xpathOperation.and(contains(text(type.getName())));
        return xpath(tag, xpathOperation).expression();
        //return xpath(tag, XpathOperation.contains(xpathAttribute)).expression();
    }
}
