package org.scadalts.e2e.page.core.criterias;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.scadalts.e2e.common.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeIdentifier;
import org.scadalts.e2e.page.core.utils.XpathFactory;

@Data
@ToString
@EqualsAndHashCode
public class NodeCriteria implements CriteriaObject {

    private final IdentifierObject identifier1;
    private final IdentifierObject identifier2;
    private final DictionaryObject type;
    private final Tag tag;
    private final CssClass cssClass;

    public NodeCriteria(IdentifierObject identifier1, IdentifierObject identifier2, DictionaryObject type, Tag tag, CssClass cssClass) {
        this.identifier1 = identifier1;
        this.identifier2 = identifier2;
        this.type = type;
        this.tag = tag;
        this.cssClass = cssClass;
    }

    @Override
    public NodeIdentifier getIdentifier() {
        return new NodeIdentifier((identifier1.getValue() + " " + identifier2.getValue()).trim());
    }

    public static NodeCriteria criteria(IdentifierObject identifier, DictionaryObject type,
                                        Tag tag) {
        return new NodeCriteria(identifier, identifier, type, tag, CssClass.empty());
    }

    public static NodeCriteria criteria(IdentifierObject identifier, DictionaryObject type,
                                        Tag tag, CssClass classCss) {
        return new NodeCriteria(identifier, identifier, type, tag, classCss);
    }

    public static NodeCriteria criteria(IdentifierObject identifier,
                                        Tag tag, CssClass classCss) {
        return new NodeCriteria(identifier, identifier, DictionaryObject.ANY, tag, classCss);
    }

    public static NodeCriteria criteria(IdentifierObject identifier, Tag tag) {
        return new NodeCriteria(identifier, identifier, DictionaryObject.ANY, tag, CssClass.empty());
    }

    public static NodeCriteria criteria(IdentifierObject identifier1, IdentifierObject identifier2, Tag tag) {
        return new NodeCriteria(identifier1, identifier2, DictionaryObject.ANY, tag, CssClass.empty());
    }

    public String getXpath() {
        if(identifier2.equals(identifier1) && type == DictionaryObject.ANY && !CssClass.empty().equals(cssClass))
            return XpathFactory.xpathTagCssClassArg(tag,cssClass,identifier1.getValue());
        if(!identifier2.equals(identifier1))
            return CssClass.empty().equals(cssClass) ?
                    XpathFactory.xpath(tag, identifier1.getValue(), identifier2.getValue()) :
                    XpathFactory.xpath(tag, cssClass, identifier1.getValue(), identifier2.getValue());
        if (type == DictionaryObject.ANY)
            return CssClass.empty().equals(cssClass) ?
                    XpathFactory.xpath(tag, identifier1.getValue()) :
                    XpathFactory.xpath(tag, cssClass, identifier1.getValue());
        return CssClass.empty().equals(cssClass) ?
                XpathFactory.xpath(tag, identifier1.getValue(), type.getName()) :
                XpathFactory.xpath(tag, cssClass, identifier1.getValue(), type.getName());
    }
}
