package org.scadalts.e2e.page.core.criterias;

import org.scadalts.e2e.common.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;

public interface NodeCriteria extends CriteriaObject {
    String getXpath();

    static NodeCriteria exactly(IdentifierObject identifier, DictionaryObject type,
                                Tag tag) {
        return new NodeCriteriaExactly(identifier, identifier, type, tag, CssClass.empty());
    }

    static NodeCriteria exactly(IdentifierObject identifier, DictionaryObject type,
                                Tag tag, CssClass classCss) {
        return new NodeCriteriaExactly(identifier, identifier, type, tag, classCss);
    }

    static NodeCriteria exactly(IdentifierObject identifier,
                                Tag tag, CssClass classCss) {
        return new NodeCriteriaExactly(identifier, identifier, DictionaryObject.ANY, tag, classCss);
    }

    static NodeCriteria exactly(IdentifierObject identifier, Tag tag) {
        return new NodeCriteriaExactly(identifier, identifier, DictionaryObject.ANY, tag, CssClass.empty());
    }

    static NodeCriteria exactly(IdentifierObject identifier1, IdentifierObject identifier2, Tag tag) {
        return new NodeCriteriaExactly(identifier1, identifier2, DictionaryObject.ANY, tag, CssClass.empty());
    }

    static NodeCriteria everyInParent(int sectionSize, int everyoneInPosition, Tag tag, CssClass cssClassParent) {
        return NodeCriteriaEvery.builder()
                .sectionSize(sectionSize)
                .everyoneInPosition(everyoneInPosition)
                .tag(tag)
                .cssClassParent(cssClassParent)
                .build();
    }

    static NodeCriteria every(int sectionSize, int everyoneInPosition, Tag tag, CssClass cssClass) {
        return NodeCriteriaEvery.builder()
                .sectionSize(sectionSize)
                .everyoneInPosition(everyoneInPosition)
                .tag(tag)
                .cssClass(cssClass)
                .build();
    }

    static NodeCriteria every(int sectionSize, int everyoneInPosition, Tag tag) {
        return NodeCriteriaEvery.builder()
                .sectionSize(sectionSize)
                .everyoneInPosition(everyoneInPosition)
                .tag(tag)
                .build();
    }
}
