package org.scadalts.e2e.page.core.criteria;


import lombok.Data;
import org.scadalts.e2e.common.dicts.E2eDictionary;

@Data
public class RowCriteria implements ObjectCriteria {

    private final String identifier;
    private final E2eDictionary type;

}
