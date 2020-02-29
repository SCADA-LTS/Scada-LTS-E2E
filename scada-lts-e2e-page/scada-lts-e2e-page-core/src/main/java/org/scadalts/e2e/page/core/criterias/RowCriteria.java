package org.scadalts.e2e.page.core.criterias;


import lombok.Data;
import org.scadalts.e2e.common.dicts.E2eDictionary;
import org.scadalts.e2e.page.core.utils.XpathFactory;

@Data
public class RowCriteria implements CriteriaObject {

    private final IdentifierObject identifier;
    private final E2eDictionary type;
    private final String tag;

    @Override
    public String getXpath() {
        return XpathFactory.xpath(tag, identifier.getValue(), type.getName());
    }
}
