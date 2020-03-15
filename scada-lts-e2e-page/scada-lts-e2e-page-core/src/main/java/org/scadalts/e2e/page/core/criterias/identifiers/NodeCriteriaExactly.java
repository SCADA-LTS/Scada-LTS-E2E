package org.scadalts.e2e.page.core.criterias.identifiers;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.scadalts.e2e.common.dicts.DictionaryObject;
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
    private final XpathAttribute cssClass;

    public NodeCriteriaExactly(IdentifierObject identifier1, IdentifierObject identifier2, DictionaryObject type, Tag tag, XpathAttribute cssClass) {
        this.identifier1 = identifier1;
        this.identifier2 = identifier2;
        this.type = type;
        this.tag = tag;
        this.cssClass = cssClass;
    }

    @Override
    public String getXpath() {
        XpathOperation xpathOperation = XpathOperation.contains(text(identifier1.getValue()));
        if(!identifier1.equals(identifier2))
            xpathOperation = xpathOperation.and(contains(text(identifier2.getValue())));
        if(type != DictionaryObject.any())
            xpathOperation = xpathOperation.and(contains(text(type.getName())));
        if(!cssClass.isEmpty())
            xpathOperation = xpathOperation.and(contains(cssClass));
        return xpath(tag, xpathOperation).expression();
    }
}
