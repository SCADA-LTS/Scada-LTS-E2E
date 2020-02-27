package org.scadalts.e2e.page.core.criteria;


import lombok.Data;
import org.scadalts.e2e.common.dicts.E2eDictionary;
import org.scadalts.e2e.page.core.util.XpathFactory;

@Data
public class RowCriteria implements ObjectCriteria {

    private final String identifier;
    private final E2eDictionary type;
    private final String tag;

    @Override
    public String getXpath() {
        return XpathFactory.xpath(tag, identifier, type.getName());
    }
}
