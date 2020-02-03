package org.scadalts.e2e.page.impl.criteria;

import lombok.Data;
import org.scadalts.e2e.page.core.criteria.ObjectCriteria;
import org.scadalts.e2e.page.impl.dict.DataSourceType;

@Data
public class DataSourceCriteria implements ObjectCriteria {

    private final String name;
    private final DataSourceType type;

}
