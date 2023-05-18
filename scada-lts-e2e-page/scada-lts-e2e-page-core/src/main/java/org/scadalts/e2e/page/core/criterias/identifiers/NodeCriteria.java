package org.scadalts.e2e.page.core.criterias.identifiers;

import org.scadalts.e2e.common.core.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.xpaths.XpathAttribute;

public interface NodeCriteria {

    String getXpath();

    NodeCriteria EMPTY = () -> "";

    static NodeCriteria exactly(IdentifierObject identifier, Tag tag) {
        return new NodeCriteriaExactly(identifier, identifier, identifier.getType(), tag, XpathAttribute.empty());
    }

    static NodeCriteria exactlyTypeAny(IdentifierObject identifier, Tag tag) {
        return new NodeCriteriaExactly(identifier, identifier, DictionaryObject.ANY, tag, XpathAttribute.empty());
    }

    static NodeCriteria exactly(IdentifierObject identifier, Tag tag, XpathAttribute attribute) {
        return new NodeCriteriaExactly(identifier, identifier, identifier.getType(), tag, attribute);
    }

    static NodeCriteria exactlyTypeAny(IdentifierObject identifier, Tag tag, XpathAttribute attribute) {
        return new NodeCriteriaExactly(identifier, identifier, DictionaryObject.ANY, tag, attribute);
    }

    static NodeCriteria exactly(Tag tag, XpathAttribute attribute) {
        return new NodeCriteriaExactly(IdentifierObject.empty(), IdentifierObject.empty(), DictionaryObject.ANY, tag, attribute);
    }

    static NodeCriteria exactly(IdentifierObject identifier1, IdentifierObject identifier2, Tag tag) {
        return new NodeCriteriaExactly(identifier1, identifier2, DictionaryObject.ANY, tag, XpathAttribute.empty());
    }

    static NodeCriteria exactly(IdentifierObject identifier1, IdentifierObject identifier2, Tag tag, XpathAttribute attribute) {
        return new NodeCriteriaExactly(identifier1, identifier2, DictionaryObject.ANY, tag, attribute);
    }

    static NodeCriteria everyInParent(int sectionSize, int everyoneInPosition, Tag parent, XpathAttribute parentAttribute) {
        return NodeCriteriaEvery.builder()
                .sectionSize(sectionSize)
                .everyoneInPosition(everyoneInPosition)
                .tag(parent)
                .attribute(XpathAttribute.empty())
                .parentAttribute(parentAttribute)
                .build();
    }

    static NodeCriteria every(int sectionSize, int everyoneInPosition, Tag tag, XpathAttribute attribute) {
        return NodeCriteriaEvery.builder()
                .sectionSize(sectionSize)
                .everyoneInPosition(everyoneInPosition)
                .tag(tag)
                .attribute(attribute)
                .parentAttribute(XpathAttribute.empty())
                .build();
    }

    static NodeCriteria every(Tag tag, XpathAttribute attribute) {
        return NodeCriteriaEvery.builder()
                .sectionSize(1)
                .everyoneInPosition(0)
                .tag(tag)
                .attribute(attribute)
                .parentAttribute(XpathAttribute.empty())
                .build();
    }

    static NodeCriteria every(int sectionSize, int everyoneInPosition, Tag tag) {
        return NodeCriteriaEvery.builder()
                .sectionSize(sectionSize)
                .everyoneInPosition(everyoneInPosition)
                .tag(tag)
                .attribute(XpathAttribute.empty())
                .parentAttribute(XpathAttribute.empty())
                .build();
    }


    static NodeCriteria withNode(Tag parent, Tag child, XpathAttribute childAttribute) {
        return NodeCriteriaWithNode.builder()
                .parent(parent)
                .child(child)
                .childAttribute(childAttribute)
                .build();
    }

    static NodeCriteria withNode(Tag parent, Tag child, XpathAttribute childAttribute, XpathAttribute parentAttribute) {
        return NodeCriteriaWithNode.builder()
                .parent(parent)
                .child(child)
                .childAttribute(childAttribute)
                .parentAttribute1(parentAttribute)
                .build();
    }

    static NodeCriteria withNode(Tag parent, Tag child, XpathAttribute childAttribute, XpathAttribute parentAttribute1,
                                 XpathAttribute parentAttribute2) {
        return NodeCriteriaWithNode.builder()
                .parent(parent)
                .child(child)
                .childAttribute(childAttribute)
                .parentAttribute1(parentAttribute1)
                .parentAttribute2(parentAttribute2)
                .build();
    }

    static NodeCriteria empty() {
        return EMPTY;
    }

    static NodeCriteria textEqual(Tag tag, XpathAttribute attribute) {
        return NodeCriteriaTextEqual.builder()
                .identifier1(IdentifierObject.empty())
                .identifier2(IdentifierObject.empty())
                .tag(tag)
                .xpathAttribute(attribute)
                .type(DictionaryObject.ANY)
                .build();
    }

    static NodeCriteria textEqual(IdentifierObject identifier, Tag tag, XpathAttribute attribute) {
        return NodeCriteriaTextEqual.builder()
                .identifier1(identifier)
                .identifier2(identifier)
                .tag(tag)
                .xpathAttribute(attribute)
                .type(DictionaryObject.ANY)
                .build();
    }
}
